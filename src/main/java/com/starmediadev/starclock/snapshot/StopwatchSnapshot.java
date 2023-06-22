package com.starmediadev.starclock.snapshot;

import com.starmediadev.starclock.Clock.CountOperation;
import com.starmediadev.starclock.impl.Stopwatch;

/**
 * The snapshot corresponding to {@link Stopwatch}'s <br>
 * Please see {@link ClockSnapshot} for more information
 */
public class StopwatchSnapshot extends ClockSnapshot {
    
    private long endTime;
    
    public StopwatchSnapshot(long time, long endTime, boolean paused, CountOperation operation, long countAmount) {
        super(time, paused, operation, countAmount);
        this.endTime = endTime;
    }
    
    /**
     * @return The end time of the stopwatch at the time of snapshot creation.
     */
    public long getEndTime() {
        return endTime;
    }
}
