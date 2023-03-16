package com.starmediadev.starclock;

public final class ClockRunnable implements Runnable {
    
    private static final ClockRunnable instance = new ClockRunnable();
    
    public static ClockRunnable getInstance() {
        return instance;
    }
    
    private ClockRunnable () {}
    
    @Override
    public void run() {
        for (Clock<?> clock : Clock.getClocks()) {
            if (clock.isPaused()) {
                continue;
            }
            
            clock.callback();
            if (!clock.isCancelled()) {
                clock.count();
            }
        }
    }
}