package com.starmediadev.starclock.condition.defaults;

import com.starmediadev.starclock.condition.ClockEndCondition;
import com.starmediadev.starclock.snapshot.StopwatchSnapshot;

public class StopwatchEndCondition implements ClockEndCondition<StopwatchSnapshot> {
    @Override
    public boolean shouldEnd(StopwatchSnapshot snapshot) {
        return snapshot.getTime() >= snapshot.getEndTime();
    }
}
