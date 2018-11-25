package com.company.Counters;

public abstract class ThreadSafeCounter {
    protected volatile int value;

    public ThreadSafeCounter(int value) {
        this.value = value;
    }

    abstract public void increase();
    abstract public void decrease();

    public int getValue() {
        return value;
    }


}
