package com.company.Locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class SpinLock implements Lock {
    private volatile boolean locked = false;
    @Override
    public void lock() {
        try {
            while (locked) {
                Thread.sleep(0);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        while (locked) {
            Thread.sleep(0);
        }
        locked = true;
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long l, TimeUnit timeUnit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        locked = false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
