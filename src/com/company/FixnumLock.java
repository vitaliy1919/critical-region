package com.company;

import java.util.concurrent.locks.Lock;

public interface FixnumLock extends Lock {
    int register();
    void unregister();
}

abstract class AbstractFixnumLock implements FixnumLock {

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
        for (int i = 0; i < numberOfThreads; i++) {
            if (registeredThreads[i] == null) {
                registeredThreads[i] = Thread.currentThread();
                threadLocal.set(i);
                return i;
            }
        }
        return -1;
    }

    @Override
    public synchronized void unregister() {
        int threadId = threadLocal.get();
        registeredThreads[threadId] = null;
    }
}
