package com.starmediadev.starclock.callback;

import com.starmediadev.starclock.Clock;
import com.starmediadev.starclock.ClockRunnable;
import com.starmediadev.starclock.snapshot.ClockSnapshot;

/**
 * A functional interface that represents an action every time a clock instance counts. 
 * @param <T> The type of the snapshot
 */
@FunctionalInterface
public interface ClockCallback<T extends ClockSnapshot> {
    /**
     * This method is called every time the clock counts with the following exceptions: <br>
     * - The callback instance is null <br>
     * - The {@link CallbackHolder}'s status flag is false (see the CallbackHolder class for more info) <br>
     * - The Holder's interval value is less than 1 <br>
     * - The time is consistent for the CountOperation's "end". This is actually a soft end, and changing the time will start again 
     * @param snapshot The snapshot for the timer. This is copies of the values and do no update if the Clock's values update
     * @return true to call this callback on the next iteration; false to stop calling this callback 
     */
    boolean callback(T snapshot);
    
    /**
     * This flag is for when this callback should be called and has nothing to do with the clock's countAmount. <br>
     * Precision is based on how often the {@link ClockRunnable} is run for the clock that this callback belongs to. <br>
     * It is recommended to choose the same unit or the next higher whole unit. If you are counting in milliseconds for the runnable, then it does not matter. <br>
     * This value can be provided in the {@link Clock#addCallback(ClockCallback, long)} method in the Clock class. <br>
     * This method is just provided as a utility or to allow users of this library to allow easier flexibility.
     * @return The interval for each time this should be called in milliseconds.
     */
    default long getInterval() {
        return 1L;
    }
}