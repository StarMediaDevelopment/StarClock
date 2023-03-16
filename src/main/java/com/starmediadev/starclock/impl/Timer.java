package com.starmediadev.starclock.impl;

import com.starmediadev.starclock.Clock;
import com.starmediadev.starclock.callback.ClockCallback;
import com.starmediadev.starclock.snapshot.TimerSnapshot;

public class Timer extends Clock<TimerSnapshot> {
    
    protected long length;
    
    public Timer(long length) {
        this(length, 1L);
    }
    
    public Timer(long length, long countAmount) {
        this(length, countAmount, null, 0L);
    }
    
    public Timer(long length, long countAmount, ClockCallback<TimerSnapshot> callback, long interval) {
        super(length, CountOperation.DOWN, countAmount, callback, interval);
        this.length = length;
    }
    
    public Timer(long length, ClockCallback<TimerSnapshot> callback, long interval) {
        this(length, getGlobalCountAmount(), callback, interval);
    }
    
    public long getLength() {
        return length;
    }
    
    public void reset() {
        this.time = length;
    }
    
    public void setLength(long length) {
        long elapsed = this.length - this.time;
        this.length = length;
        this.time = Math.max(this.length - elapsed, 0);
    }
    
    public void addLength(long length) {
        setLength(this.length + length);
    }
    
    public void removeLength(long length) {
        if (length - this.length < 0) {
            length = this.length;
        }
        
        setLength(this.length - length);
    }
    
    @Override
    public Timer start() {
        return (Timer) super.start();
    }
    
    @Override
    public TimerSnapshot createSnapshot() {
        return new TimerSnapshot(this.time, this.paused, this.length, this.operation, getCountAmount());
    }
}