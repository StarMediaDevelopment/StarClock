package com.starmediadev.starclock.impl;

import com.starmediadev.starclock.Clock;
import com.starmediadev.starclock.snapshot.StopwatchSnapshot;

/**
 * This is your typical count-up type of clock. <br>
 * The {@link Clock#addTime(long)} adds time as if it had counted that time <br>
 * The {@link Clock#removeTime(long)} method removes time as if it had not counted that amount of time. <br>
 * The {@link Clock#setTime(long)} method sets the current time and the stopwatch will count-up starting with the new value.
 */
public class Stopwatch extends Clock<StopwatchSnapshot> {
    
    private long endTime;
    
    /**
     * Constructs a new Stopwatch
     * @param endTime The end time of the stopwatch. This is how many milliseconds that the clock should count to, and not the timestamp
     * @param countAmount The count amount. See the {@link Clock} documentation for how this works
     */
    public Stopwatch(long endTime, long countAmount) {
        super(0L, CountOperation.DOWN, countAmount);
        this.endTime = endTime;
    }
    
    @Override
    public StopwatchSnapshot createSnapshot() {
        return new StopwatchSnapshot(time, endTime, paused, operation, getCountAmount());
    }
    
    @Override
    public void count() {
        if (this.time <= this.endTime) {
            super.count();
        }
    }
    
    @Override
    public Stopwatch start() {
        return (Stopwatch) super.start();
    }
    
    /**
     * @return The time that this clock will end at. This will simply stop calling the Callbacks until the time value is changed to be lower
     */
    public long getEndTime() {
        return endTime;
    }
    
    /**
     * Sets the new endTime of this Stopwatch. <br>
     * If the new endTime is less that the previous one, this does nothing. If it is more, the Stopwatch will continue
     * @param endTime The new endTime
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}