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

    public int getNumberOfThreads() {
        return numberOfThreads;
    }

    // Each thread should get Id
    // If return value is -1, It means thread can't access the resource
    @Override
    public synchronized int register() {
        Integer localId = threadLocal.get();
        if (localId != null) {
            return localId;
        }
        synchronized (this) {
            for (int i = 0; i < numberOfThreads; i++) {
                if (registeredThreads[i] == null) {
                    registeredThreads[i] = Thread.currentThread();
                    threadLocal.set(i);
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public synchronized void unregister() throws RuntimeException {
        Integer localId = threadLocal.get();
        if (localId == null) {
            throw new RuntimeException("This thread is not registered, so it can't be unregistered");
        }
        registeredThreads[localId] = null;
        threadLocal.remove();
    }

    @Override
    public int getId() {
        return threadLocal.get();
    }

    @Override
    public void lock() {
        int id = register();
        lock(id);
    }

    public abstract void lock(int id);

    @Override
    public void unlock() {
        int id = register();
        unlock(id);
    }

    public abstract void unlock(int id);

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
