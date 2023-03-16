package com.starmediadev.starclock.impl;

import com.starmediadev.starclock.Clock;
import com.starmediadev.starclock.callback.ClockCallback;
import com.starmediadev.starclock.snapshot.StopwatchSnapshot;

public class Stopwatch extends Clock<StopwatchSnapshot> {
    
    private long endTime;
    
    public Stopwatch(long endTime, long countAmount) {
        this(0L, endTime, countAmount, null, 0L);
    }
    
    public Stopwatch(long endTime) {
        this(0L, endTime, getGlobalCountAmount(), null, 0L);
    }
    
    public Stopwatch(long startTime, long endTime, long countAmount, ClockCallback<StopwatchSnapshot> callback, long interval) {
        super(startTime, CountOperation.UP, countAmount, callback, interval);
        this.endTime = endTime;
    }
    
    public Stopwatch(long startTime, long endTime, ClockCallback<StopwatchSnapshot> callback, long interval) {
        super(startTime, CountOperation.UP, getGlobalCountAmount(), callback, interval);
        this.endTime = endTime;
    }
    
    @Override
    public StopwatchSnapshot createSnapshot() {
        return new StopwatchSnapshot(time, endTime, paused, operation, getCountAmount());
    }
    
    @Override
    public Stopwatch start() {
        return (Stopwatch) super.start();
    }
    
    public long getEndTime() {
        return endTime;
    }
    
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}