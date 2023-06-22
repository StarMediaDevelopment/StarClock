package com.starmediadev.starclock;

import com.starmediadev.starclock.callback.*;
import com.starmediadev.starclock.condition.ClockEndCondition;
import com.starmediadev.starclock.snapshot.ClockSnapshot;

import java.util.*;

/**
 * This is the parent class for all Clocks and provides the main functionality and definitions for this Library
 * @param <T> The type for the ClockSnapshot instance
 */
public abstract class Clock<T extends ClockSnapshot> {
    protected long time;
    protected boolean paused = true;
    protected boolean cancelled;
    protected Map<UUID, CallbackHolder<T>> callbacks = new HashMap<>();
    protected final CountOperation operation;
    private final long countAmount;
    protected ClockEndCondition<T> endCondition;
    
    /**
     * Constructs a new Clock
     * @param time The time to start at. This value changes as the clock progresses
     * @param operation The direction of counting for this clock
     * @param countAmount The amount to count by. This is provided by default using the factory methods in the {@link ClockManager} class and is cached for easier use.
     */
    public Clock(long time, CountOperation operation, long countAmount) {
        this.time = time;
        this.operation = operation;
        this.countAmount = countAmount;
    }
    
    /**
     * @return The cached amount to count by.
     */
    public final long getCountAmount() {
        return countAmount;
    }
    
    /**
     * Logic for counting based on the {@code countAmount} and the {@link CountOperation} for this class. <br>
     * This does not allow the {@code time} to go below 0
     */
    public void count() {
        if (this.operation == CountOperation.UP) {
            this.time += getCountAmount();
        } else {
            this.time = Math.max(0, this.time - getCountAmount());
        }
    }
    
    /**
     * Creates a ClockSnapshot for this Clock instance
     * @return The snapshot instance
     */
    public abstract T createSnapshot();
    
    protected boolean shouldCallback(CallbackHolder<T> holder) {
        if (holder.getLastRun() == 0) {
            return true;
        }
        
        if (this.operation == CountOperation.UP) {
            return this.time >= holder.getLastRun() - holder.getInterval();
        } else {
            return this.time <= holder.getLastRun() - holder.getInterval();
        }
    }
    
    /**
     * Contains logic for processing the {@link ClockCallback}'s. Please see the ClockCallback and the {@link CallbackHolder} classes for how this is processed
     */
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
            if (endCondition.shouldEnd(snapshot)) {
                cancel();
            }
        } 
    }
    
    /**
     * This marks the start for the clock, please note: the clock will not start if no {@link ClockRunnable} is actually being ran.
     * @return The instance of the clock (Utility return value)
     */
    public Clock<T> start() {
        unpause();
        return this;
    }
    
    /**
     * Pauses the clock
     */
    public void pause() {
        this.paused = true;
    }
    
    /**
     * Resumes the clock
     */
    public void unpause() {
        this.paused = false;
    }
    
    /**
     * Marks for cancellation for the next run of the {@link ClockRunnable} <br>
     * Between calling this method and until it processes it again, it can be uncancelled
     */
    public void cancel() {
        this.cancelled = true;
    }
    
    /**
     * Removes the mark for cancellation. See the cancel() method
     */
    public void uncancel() {
        this.cancelled = false;
    }
    
    /**
     * Checks to see if it is marked for cancellation. If you call the uncancel() method before calling this one, you will get inaccurate results
     * @return If this clock is marked for cancellation
     */
    public boolean isCancelled() {
        return cancelled;
    }
    
    /**
     * The time is the value that is updated in the clock in milliseconds.
     * @return The current time for the clock
     */
    public long getTime() {
        return time;
    }
    
    /**
     * @return If this clock is currently paused.
     */
    public boolean isPaused() {
        return paused;
    }
    
    /**
     * Adds time to this clock. Please see the implementations for how this method affects them.
     * @param time The time to add
     */
    public void addTime(long time) {
        this.time += time;
    }
    
    /**
     * Removes time from this clock. Please see the implementations for how this method affects them.
     * @param time The time to remove
     */
    public void removeTime(long time) {
        this.time -= time;
    }
    
    /**
     * Sets the time of this clock. Please see the implementations for how this method affects them.
     * @param time The time to set
     */
    public void setTime(long time) {
        this.time = time;
    }
    
    /**
     * Adds a callback to this clock
     * @param callback The callback
     * @param interval The interval to run the callback at.
     * @return The UUID for the callback (Mostly internal) and for removing a callback
     */
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
    
    /**
     * Adds a callback to this clock
     * @param callback The callback
     * @return The UUID for the callback (Mostly internal) and for removing a callback
     */
    public UUID addCallback(ClockCallback<T> callback) {
        return addCallback(callback, callback.getInterval());
    }
    
    /**
     * Removes the callback from this Clock 
     * @param uuid The uuid of the callback
     */
    public void removeCallback(UUID uuid) {
        this.callbacks.remove(uuid);
    }
    
    /**
     * See the {@link ClockEndCondition} documentation for how this works
     * @param endCondition The end condition
     */
    public void setEndCondition(ClockEndCondition<T> endCondition) {
        this.endCondition = endCondition;
    }
    
    /**
     * @return The current end condition. This can be null.
     */
    public ClockEndCondition<T> getEndCondition() {
        return endCondition;
    }
    
    /**
     * @param uuid The UUID of the callback
     * @return The callback instance
     */
    public ClockCallback<T> getCallback(UUID uuid) {
        CallbackHolder<T> holder = this.callbacks.get(uuid);
        if (holder != null) {
            return holder.getCallback();
        }
        
        return null;
    }
    
    /**
     * This resets the CallbackHolder status flag to true to allow them to run again. <br>
     * See {@link CallbackHolder#getStatus()} for more information
     */
    public void resetCallbackStatus() {
        this.callbacks.values().forEach(holder -> holder.setStatus(true));
    }
    
    public enum CountOperation {
        UP, DOWN
    }
}