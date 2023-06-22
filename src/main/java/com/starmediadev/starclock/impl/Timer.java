package com.starmediadev.starclock.impl;

import com.starmediadev.starclock.Clock;
import com.starmediadev.starclock.snapshot.TimerSnapshot;

/**
 * This is your typical count-down type of clock. <br>
 * The {@link Clock#addTime(long)} adds time as if it had not counted that time, keeping the previous elapsed time <br>
 * The {@link Clock#removeTime(long)} method removes time as if it had counted that amount of time. <br>
 * The {@link Clock#setTime(long)} method sets the current time and the timer will count-down starting with the new value. <br>
 * There are some new methods for manipulating time, please see that documentation
 */
public class Timer extends Clock<TimerSnapshot> {
    
    protected long length;
    
    /**
     * Constructs a new Timer
     * @param length The starting length of the timer
     * @param countAmount The count amount. See the {@link Clock} documentation for how this works
     */
    public Timer(long length, long countAmount) {
        super(length, CountOperation.DOWN, countAmount);
        this.length = length;
    }
    
    /**
     * @return The length of this Timer
     */
    public long getLength() {
        return length;
    }
    
    /**
     * Resets the current count back to the length
     */
    public void reset() {
        this.time = length;
    }
    
    /**
     * Sets the new length of this timer, keeping previous elapsed time. Will not go below 0 though <br>
     * If you want it to start over, use the setLengthAndReset() method, which does not preserve elapsed time.
     * @param length The new length
     */
    public void setLength(long length) {
        long elapsed = this.length - this.time;
        this.length = length;
        this.time = Math.max(this.length - elapsed, 0);
    }
    
    /**
     * Sets the new length and resets the time to the new length as well, loosing the elapsed time.
     * @param length The new length
     */
    public void setLengthAndReset(long length) {
        this.length = length;
        this.reset();
    }
    
    /**
     * Adds length to this Timer. This will preserve elapsed time.
     * @param length The length to add
     */
    public void addLength(long length) {
        setLength(this.length + length);
    }
    
    /**
     * Removes length from this Timer. This will preserve elapsed time.
     * @param length The length to remove
     */
    public void removeLength(long length) {
        if (length - this.length < 0) {
            length = this.length;
        }
        
        setLength(this.length - length);
    }
    
    @Override
    public Timer start() {
        return (Timer) super.start();
    }
    
    @Override
    public TimerSnapshot createSnapshot() {
        return new TimerSnapshot(this.time, this.paused, this.length, this.operation, getCountAmount());
    }
}