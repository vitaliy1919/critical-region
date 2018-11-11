package com.company.Counters;

import java.util.concurrent.locks.Lock;

public class LockBasedCounter extends ThreadSafeCounter {
    Lock lock;
    public LockBasedCounter(Lock lock, int value) {
        super(value);
        this.lock = lock;
    }

    @Override
    public void increase() {
        lock.lock();
        value[0]++;
        lock.unlock();
    }

    @Override
    public void decrease() {
        lock.lock();
        value[0]--;
        lock.unlock();
    }
}
