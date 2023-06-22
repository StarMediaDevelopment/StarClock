package com.starmediadev.starclock.snapshot;

import com.starmediadev.starclock.Clock.CountOperation;
import com.starmediadev.starclock.callback.ClockCallback;

/**
 * This class represents a snapshot of a clock. This caches the current values in the clock to allow read-only access to them at the time it is used. <br>
 * The values are final and even if modification happens through reflection, it does not affect the clock that it came from. <br>
 * This class is used within {@link ClockCallback}'s. as their main functionality. <br>
 * There should be a ClockSnapshot instance for each type of Clock. The two default implementations has corresponding snapshots
 */
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
    
    /**
     * @return The time of the clock at the moment this snapshot was created
     */
    public long getTime() {
        return time;
    }
    
    /**
     * @return The paused status of the clock at the moment this snapshot was created
     */
    public boolean isPaused() {
        return paused;
    }
    
    /**
     * @return The CountOperaton of the clock
     */
    public CountOperation getOperation() {
        return operation;
    }
    
    /**
     * @return The count amount of the clock
     */
    public long getCountAmount() {
        return countAmount;
    }
}
