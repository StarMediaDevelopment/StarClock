package com.starmediadev.starclock.condition.defaults;

import com.starmediadev.starclock.condition.ClockEndCondition;
import com.starmediadev.starclock.impl.Stopwatch;
import com.starmediadev.starclock.snapshot.StopwatchSnapshot;

/**
 * Default implementation for the {@link Stopwatch} ending based on the end time and current time. <br>
 * This is not applied by default to Stopwatches and is provided as a default implementation for easier use
 */
public class StopwatchEndCondition implements ClockEndCondition<StopwatchSnapshot> {
    @Override
    public boolean shouldEnd(StopwatchSnapshot snapshot) {
        return snapshot.getTime() >= snapshot.getEndTime();
    }
}
