package com.starmediadev.starclock;

import com.starmediadev.starclock.impl.*;
import com.starmediadev.starclock.impl.Timer;
import com.starmediadev.starclock.snapshot.ClockSnapshot;

import java.util.*;
import java.util.logging.Logger;

/**
 * This class allows for easy access to creating and managing {@link Clock}'s <br>
 * You must handle the registration of the {@link ClockRunnable} instance provided by the getter for it. <br>
 * There is no default registration to allow flexibility for different circumstances and use cases. <br>
 * One thing to note: {@code countAmount} is in milliseconds. This should match the amount used for scheduling purposes. <br>
 * Clocks cache the {@code countAmount} when created, so changing it, will not change it for exisiting clocks. <br>
 * It is recommended to use the StarLib {@code Scheduler} API as that is what it was kind of designed for, however it is not a requirement, which is the reason that it is provided as a {@link Runnable}. <br>
 * Operations are Thread-Safe as it uses internal syncronization where appropriate and copies where it is easier
 */
public class ClockManager {
    protected final List<Clock<? extends ClockSnapshot>> clocks = Collections.synchronizedList(new ArrayList<>());
    protected long countAmount;
    protected ClockRunnable runnable;
    protected Logger logger;
    
    /**
     * Constructs a new ClockManager instance.
     * @param logger The logger to be used (Currently has no actual use, yet)
     * @param countAmount The amount of milliseconds to count by for the clocks that are registered to this Manager
     */
    public ClockManager(Logger logger, long countAmount) {
        this.runnable = new ClockRunnable(this);
        this.countAmount = countAmount;
        this.logger = logger;
    }
    
    /**
     * @return The logger instance
     */
    public Logger getLogger() {
        return logger;
    }
    
    /**
     * Adds a clock to this manager.
     * @param clock The clock to add
     */
    public void addClock(Clock<? extends ClockSnapshot> clock) {
        this.clocks.add(clock);
    }
    
    /**
     * Removes a clock from this manager
     * @param clock The clock to remove, this must be an exact instance
     */
    public void removeClock(Clock<? extends ClockSnapshot> clock) {
        this.clocks.remove(clock);
    }
    
    /**
     * Factory method to create a {@link Timer} and register the timer with this Manager
     * @param length The length of time to run the timer in milliseconds.
     * @return The timer instance
     */
    public Timer createTimer(long length) {
        Timer timer = new Timer(length, countAmount);
        addClock(timer);
        return timer;
    }
    
    /**
     * Factory method to create a {@link Stopwatch} and register the stopwatch with this Manager
     * @param endTime The end time of the stopwatch in milliseconds.
     * @return The stopwatch instance
     */
    public Stopwatch createStopwatch(long endTime) {
        Stopwatch stopwatch = new Stopwatch(endTime, countAmount);
        addClock(stopwatch);
        return stopwatch;
    }
    
    /**
     * @return The runnable instance for this ClockManager
     */
    public ClockRunnable getRunnable() {
        return runnable;
    }
    
    /**
     * @return All active clocks registered to this Manager
     */
    public List<Clock<? extends ClockSnapshot>> getClocks() {
        return clocks;
    }
    
    /**
     * @return The amount in milliseconds that clocks count by that are registered to this Manager
     */
    public long getCountAmount() {
        return countAmount;
    }
    
    /**
     * Sets the amount of milliseconds that future clocks count by that are registered to this Manager
     * @param countAmount The count amount in milliseconds
     */
    public void setCountAmount(long countAmount) {
        this.countAmount = countAmount;
    }
}