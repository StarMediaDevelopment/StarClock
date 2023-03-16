package com.starmediadev.starclock.callback;

import com.starmediadev.starclock.snapshot.ClockSnapshot;

@FunctionalInterface
public interface ClockCallback<T extends ClockSnapshot> {
    boolean callback(T snapshot);
}