package com.starmediadev.starclock.condition.defaults;

import com.starmediadev.starclock.condition.ClockEndCondition;
import com.starmediadev.starclock.impl.Stopwatch;
import com.starmediadev.starclock.impl.Timer;
import com.starmediadev.starclock.snapshot.TimerSnapshot;

/**
 * Default implementation for the {@link Timer} ending based on if the time hits 0. <br>
 * This is not applied by default to Timers and is provided as a default implementation for easier use
 */
public class TimerEndCondition implements ClockEndCondition<TimerSnapshot> {
    @Override
    public boolean shouldEnd(TimerSnapshot snapshot) {
        return snapshot.getTime() <= 0;
    }
}
