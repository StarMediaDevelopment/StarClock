package com.starmediadev.starclock;

import java.util.ArrayList;

/**
 * The Runnable for clocks. This extends the java {@link Runnable} interface to allow use in a multi-threaded environment <br>
 * {@link ClockManager}'s create an instance of this class by default. You shouldn't have to create one yourself.
 */
public final class ClockRunnable implements Runnable {
    
    private ClockManager clockManager;
    
    public ClockRunnable(ClockManager clockManager) {
        this.clockManager = clockManager;
    }
    
    @Override
    public void run() {
        for (Clock<?> clock : new ArrayList<>(clockManager.getClocks())) {
            if (clock.isPaused()) {
                continue;
            }
            
            clock.callback();
            if (!clock.isCancelled()) {
                clock.count();
            } else {
                clockManager.getClocks().remove(clock);
            }
        }
    }
}