package com.company.Counters;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter extends ThreadSafeCounter {
    private final AtomicInteger integer;

    public AtomicCounter(int value) {
        super(value);
        integer = new AtomicInteger(value);
    }

    @Override
    public void increase() {
        integer.incrementAndGet();
    }

    @Override
    public void decrease() {
        integer.decrementAndGet();
    }

    @Override
    public int getValue() {
        return integer.intValue();
    }
}
