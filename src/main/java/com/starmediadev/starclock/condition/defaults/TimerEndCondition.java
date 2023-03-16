package com.starmediadev.starclock.condition.defaults;

import com.starmediadev.starclock.condition.ClockEndCondition;
import com.starmediadev.starclock.snapshot.TimerSnapshot;

public class TimerEndCondition implements ClockEndCondition<TimerSnapshot> {
    @Override
    public boolean shouldEnd(TimerSnapshot snapshot) {
        return snapshot.getTime() <= 0;
    }
}
