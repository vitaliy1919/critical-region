package com.company.Locks;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Mutex implements Lock {
    private Semaphore semaphore = new Semaphore(1);
    @Override
    public void lock() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        semaphore.acquire();
    }

    @Override
    public boolean tryLock() {
        return semaphore.tryAcquire();
    }

    @Override
    public boolean tryLock(long l, TimeUnit timeUnit) throws InterruptedException {
        return semaphore.tryAcquire(l, timeUnit);
    }

    @Override
    public void unlock() {
        semaphore.release();
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
