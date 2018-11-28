package com.company.Counters;

public class MonitorCounter extends ThreadSafeCounter {
    public MonitorCounter(int value) {
        super(value);
    }

    @Override
    public void increase() {
        synchronized (this) {
            value++;
        }
    }

    @Override
    public void decrease() {
        synchronized (this) {
            value--;
        }
    }
}
