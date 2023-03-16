package com.starmediadev.starclock;

import com.starmediadev.starclock.callback.*;
import com.starmediadev.starclock.condition.ClockEndCondition;
import com.starmediadev.starclock.snapshot.ClockSnapshot;

import java.util.*;

public abstract class Clock<T extends ClockSnapshot> {
    private static final List<Clock<? extends ClockSnapshot>> CLOCKS = Collections.synchronizedList(new ArrayList<>());
    
    private static long globalCountAmount;
    
    protected long time;
    protected boolean paused = true;
    protected boolean cancelled;
    protected Map<UUID, CallbackHolder<T>> callbacks = new HashMap<>();
    protected CountOperation operation;
    private long countAmount;
    protected ClockEndCondition<T> endCondition;
    
    public Clock() {
        CLOCKS.add(this);
    }
    
    public Clock(long time, CountOperation operation) {
        this();
        this.time = time;
        this.operation = operation;
    }
    
    public Clock(long time, CountOperation operation, long countAmount) {
        this(time, operation);
        this.countAmount = countAmount;
    }
    
    public Clock(long time, CountOperation operation, long countAmount, ClockCallback<T> callback, long interval) {
        this(time, operation, countAmount);
        addCallback(callback, interval);
    }
    
    public Clock(long time, CountOperation operation, long countAmount, ClockCallback<T> callback, long interval, ClockEndCondition<T> endCondition) {
        this(time, operation, countAmount, callback, interval);
        this.endCondition = endCondition;
    }
    
    public final long getCountAmount() {
        if (globalCountAmount > 0) {
            return globalCountAmount;
        }
        
        return countAmount;
    }
    
    public final void count() {
        if (this.operation == null) {
            return;
        }
        
        if (this.operation == CountOperation.UP) {
            this.time += getCountAmount();
        } else {
            this.time = Math.max(0, this.time - getCountAmount());
        }
    }
    
    public abstract T createSnapshot();
    
    protected boolean shouldCallback(CallbackHolder<T> holder) {
        if (holder.getLastRun() == 0) {
            return true;
        }
        
        if (this.operation == null) {
            return true;
        }
        
        if (this.operation == CountOperation.UP) {
            return this.time >= holder.getLastRun() - holder.getInterval();
        } else {
            return this.time <= holder.getLastRun() - holder.getInterval();
        }
    }
    
    public void callback() {
        if (this.callbacks.isEmpty()) {
            return;
        }
        
        T snapshot = createSnapshot();
        for (CallbackHolder<T> holder : this.callbacks.values()) {
            ClockCallback<T> callback = holder.getCallback();
            if (callback == null) {
                continue;
            }
            
            if (!holder.getStatus()) {
                continue;
            }
            
            if (holder.getInterval() <= 0) {
                continue;
            }
            
            if (!shouldCallback(holder)) {
                continue;
            }
            
            holder.setLastRun(time);
            boolean callbackResult = callback.callback(snapshot);
            holder.setStatus(callbackResult);
        }
        
        if (endCondition != null) {
            if (!endCondition.shouldEnd(snapshot)) {
                cancel();
            }
        }
    }
    
    public Clock<T> start() {
        unpause();
        return this;
    }
    
    public void pause() {
        this.paused = true;
    }
    
    public void unpause() {
        this.paused = false;
    }
    
    public void cancel() {
        CLOCKS.remove(this);
        this.cancelled = true;
    }
    
    public void uncancel() {
        CLOCKS.add(this);
        this.cancelled = false;
    }
    
    public boolean isCancelled() {
        return cancelled;
    }
    
    public long getTime() {
        return time;
    }
    
    public boolean isPaused() {
        return paused;
    }
    
    public void addTime(long time) {
        this.time += time;
    }
    
    public void removeTime(long time) {
        this.time -= time;
    }
    
    public void setTime(long time) {
        this.time = time;
    }
    
    public UUID addCallback(ClockCallback<T> callback, long interval) {
        if (callback == null) {
            return null;
        }
        UUID uuid;
        do {
            uuid = UUID.randomUUID();
        } while (this.callbacks.containsKey(uuid));
        
        this.callbacks.put(uuid, new CallbackHolder<>(callback, uuid, interval));
        return uuid;
    }
    
    public void setEndCondition(ClockEndCondition<T> endCondition) {
        this.endCondition = endCondition;
    }
    
    public ClockEndCondition<T> getEndCondition() {
        return endCondition;
    }
    
    public ClockCallback<T> getCallback(UUID uuid) {
        CallbackHolder<T> holder = this.callbacks.get(uuid);
        if (holder != null) {
            return holder.getCallback();
        }
        
        return null;
    }
    
    public static List<Clock<? extends ClockSnapshot>> getClocks() {
        return new ArrayList<>(CLOCKS);
    }
    
    public enum CountOperation {
        UP, DOWN
    }
    
    public static void setGlobalCountAmount(long globalCountAmount) {
        Clock.globalCountAmount = globalCountAmount;
    }
    
    public static long getGlobalCountAmount() {
        return globalCountAmount;
    }
}