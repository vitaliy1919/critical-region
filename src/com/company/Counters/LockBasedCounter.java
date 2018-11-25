package com.company.Counters;

import java.util.concurrent.locks.Lock;

public class LockBasedCounter extends ThreadSafeCounter {
    private Lock lock;
    public LockBasedCounter(Lock lock, int value) {
        super(value);
        this.lock = lock;
    }

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void increase() {
        lock.lock();
        value++;
        lock.unlock();
    }

    @Override
    public void decrease() {
        lock.lock();
        value--;
        lock.unlock();
    }
}
