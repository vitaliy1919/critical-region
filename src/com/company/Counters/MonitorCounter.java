package com.company.Counters;

public class MonitorCounter extends ThreadSafeCounter {
    public MonitorCounter(int value) {
        super(value);
    }

    @Override
    public void increase() {
        synchronized (value) {
            value[0]++;
        }
    }

    @Override
    public void decrease() {
        synchronized (value) {
            value[0]--;
        }
    }
}
