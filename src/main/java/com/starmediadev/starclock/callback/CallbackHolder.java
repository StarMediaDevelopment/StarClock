package com.starmediadev.starclock.callback;

import com.starmediadev.starclock.Clock;
import com.starmediadev.starclock.snapshot.ClockSnapshot;

import java.util.UUID;

/**
 * This class is mainly for internal use, however, this class is used for some checks and logic for Clocks <br>
 * @param <T> The Snapshot type
 */
public class CallbackHolder<T extends ClockSnapshot> {
    protected final ClockCallback<T> callback;
    protected final UUID callbackId;
    protected final long interval;
    protected long lastRun;
    protected boolean status = true;
    
    /**
     * Constructs a new CallbackHolder
     * @param callback The callback reference
     * @param callbackId The ID of the callback
     * @param interval The interval that the callback runs at in milliseconds
     */
    public CallbackHolder(ClockCallback<T> callback, UUID callbackId, long interval) {
        this.callback = callback;
        this.callbackId = callbackId;
        this.interval = interval;
    }
    
    /**
     * @return The callback instance
     */
    public ClockCallback<T> getCallback() {
        return callback;
    }
    
    /**
     * @return The interval in milliseconds
     */
    public long getInterval() {
        return interval;
    }
    
    /**
     * @return The last time that the callback ran based on the clock's time, in milliseconds
     */
    public long getLastRun() {
        return lastRun;
    }
    
    /**
     * Sets the last run time of the clock, based on the clock's time
     * @param lastRun The new last run time
     */
    public void setLastRun(long lastRun) {
        this.lastRun = lastRun;
    }
    
    /**
     * Sets the status of the holder. See {@link CallbackHolder#getStatus()} to see what this flag does.
     * @param status The new status
     */
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    /**
     * The status is a flag that controls if the callback should continue or not. This is controlled by returning true or false in the {@link ClockCallback#callback(ClockSnapshot)} method. <br>
     * When returning false from that method, it will not be called again for the duration of the clock, unless you call the {@link Clock#resetCallbackStatus()} method
     * @return The current status
     */
    public boolean getStatus() {
        return status;
    }
}
