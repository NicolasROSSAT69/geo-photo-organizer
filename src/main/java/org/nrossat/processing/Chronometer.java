package org.nrossat.processing;

public class Chronometer {
    private long startTime;

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public long stop() {
        return System.currentTimeMillis() - startTime;
    }
}
