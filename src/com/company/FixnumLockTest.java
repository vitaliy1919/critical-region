package com.company;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

import static org.junit.jupiter.api.Assertions.*;

class AbstractFixnumLockTest {

    @org.junit.jupiter.api.Test
    void mainTest() {
        AbstractFixnumLock lock = new MockFixnumLock(2);

        Thread threadOne = new Thread() {
            public void run() {
                try {
                    int id = lock.register();
                    assertEquals(id, 0);
                    Thread.sleep(2000);
                    lock.unregister();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread threadTwo = new Thread() {
            public void run() {
                try {
                    Thread.sleep(200);
                    int id = lock.register();
                    assertEquals(id, 1);
                    Thread.sleep(1000);
                    lock.unregister();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread threadThree = new Thread() {
            public void run() {
                try {
                    Thread.sleep(400);
                    int id = lock.register();
                    assertEquals(id, -1);
                    Thread.sleep(1000);
                    lock.unregister();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread threadFour = new Thread() {
            public void run() {
                try {
                    Thread.sleep(1500);
                    int id = lock.register();
                    assertEquals(id, 1);
                    Thread.sleep(1000);
                    lock.unregister();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        threadOne.start();
        threadTwo.start();
        threadThree.start();
        threadFour.start();
    }
}

class MockFixnumLock extends AbstractFixnumLock {

    public MockFixnumLock(int numberOfThreads) {
        super(numberOfThreads);
    }

    @Override
    public void lock() {
        return;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        return;
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        return;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}