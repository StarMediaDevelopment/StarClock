package com.starmediadev.starclock.snapshot;

import com.starmediadev.starlib.clock.Clock.CountOperation;

public class StopwatchSnapshot extends ClockSnapshot {
    
    private long endTime;
    
    public StopwatchSnapshot(long time, long endTime, boolean paused, CountOperation operation, long countAmount) {
        super(time, paused, operation, countAmount);
        this.endTime = endTime;
    }
    
    public long getEndTime() {
        return endTime;
    }
}
