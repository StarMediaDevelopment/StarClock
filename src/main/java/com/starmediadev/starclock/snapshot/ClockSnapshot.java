package com.starmediadev.starclock.snapshot;

import com.starmediadev.starlib.clock.Clock.CountOperation;

public abstract class ClockSnapshot {
    protected final long time;
    protected final boolean paused;
    protected final CountOperation operation;
    protected final long countAmount;
    
    public ClockSnapshot(long time, boolean paused, CountOperation operation, long countAmount) {
        this.time = time;
        this.paused = paused;
        this.operation = operation;
        this.countAmount = countAmount;
    }
    
    public long getTime() {
        return time;
    }
    
    public boolean isPaused() {
        return paused;
    }
}
