package com.company.FixnumLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public abstract class AbstractFixnumLock implements FixnumLock {

    private int numberOfThreads;
    private Thread[] registeredThreads;
    private ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();

    public AbstractFixnumLock(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
        registeredThreads = new Thread[numberOfThreads];
    }

    // Each thread should get Id
    // If return value is -1, It means thread can't access the resource
    @Override
    public synchronized int register() {
        int freePlace = -1;
        for (int i = 0; i < numberOfThreads; i++) {
            if (registeredThreads[i] == null && freePlace == -1) {
                freePlace = i;
            }
            if (registeredThreads[i] == Thread.currentThread()) {
                return i;
            }
        }
        if (freePlace != -1) {
            registeredThreads[freePlace] = Thread.currentThread();
            threadLocal.set(freePlace);
        }
        return freePlace;
    }

    @Override
    public synchronized void unregister() throws RuntimeException {
        for (int i = 0; i < numberOfThreads; i++) {
            if (registeredThreads[i] == Thread.currentThread()) {
                threadLocal.remove();
                registeredThreads[i] = null;
                return;
            }
        }
        throw new RuntimeException("This thread is not registered, so it can't be unregistered");
    }

    @Override
    public void lock() {
        int id = register();
        lock(id);
    }

    public abstract void lock(int id);

    @Override
    public void lockInterruptibly() throws InterruptedException {
        int id = register();
        lockInterruptibly(id);
    }

    public abstract void lockInterruptibly(int id) throws InterruptedException;

    @Override
    public boolean tryLock() {
        int id = register();
        return tryLock(id);
    }

    public abstract boolean tryLock(int id);

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        int id = register();
        return tryLock(id, time, unit);
    }

    public abstract boolean tryLock(int id, long time, TimeUnit unit) throws InterruptedException;

    @Override
    public Condition newCondition() {
        return null;
    }
}
