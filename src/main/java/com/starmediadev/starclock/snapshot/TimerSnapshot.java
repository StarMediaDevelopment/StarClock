package com.starmediadev.starclock.snapshot;

import com.starmediadev.starclock.Clock.CountOperation;

public class TimerSnapshot extends ClockSnapshot {
    
    protected final long length;
    
    public TimerSnapshot(long time, boolean paused, long length, CountOperation operation, long countAmount) {
        super(time, paused, operation, countAmount);
        this.length = length;
    }
    
    /**
     * @return The length of the clock at the snapshot
     */
    public long getLength() {
        return length;
    }
}
