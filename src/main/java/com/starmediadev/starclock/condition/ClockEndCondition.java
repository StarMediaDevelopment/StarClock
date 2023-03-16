package com.starmediadev.starclock.condition;

import com.starmediadev.starclock.snapshot.ClockSnapshot;

@FunctionalInterface
public interface ClockEndCondition<T extends ClockSnapshot> {
    boolean shouldEnd(T snapshot);
}
