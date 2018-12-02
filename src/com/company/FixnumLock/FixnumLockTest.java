package com.company.FixnumLock;

import org.omg.SendingContext.RunTime;

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

    @org.junit.jupiter.api.Test
    void testUnregisterMethod() {
        AbstractFixnumLock lock = new MockFixnumLock(2);

        Thread threadOne = new Thread() {
            public void run() {
                try {
                    lock.unregister();
                } catch (RuntimeException e) {
                    assertEquals(e.getMessage(), "This thread is not registered, so it can't be unregistered");
                }
            }
        };

        Thread threadTwo = new Thread() {
            public void run() {
                try {
                    lock.lock();
                    lock.unregister();
                } catch (RuntimeException e) {
                    assertFalse(true);
                }
            }
        };

        threadOne.start();
        threadTwo.start();
    }

    @org.junit.jupiter.api.Test
    void testRegisterMethod() {
        AbstractFixnumLock lock = new MockFixnumLock(2);

        Thread threadOne = new Thread() {
            public void run() {
                try {
                    int id = lock.register();
                    assertEquals(id, 0);
                    Thread.sleep(200);
                    id = lock.register();
                    assertEquals(id, 0);
                    Thread.sleep(200);
                    id = lock.register();
                    assertEquals(id, 0);
                    lock.unregister();
                } catch (Exception e) {
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
                    lock.unregister();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        threadOne.start();
        threadTwo.start();
    }
}

class MockFixnumLock extends AbstractFixnumLock {

    MockFixnumLock(int numberOfThreads) {
        super(numberOfThreads);
    }

    @Override
    public void lock(int id) {
    }

    @Override
    public void unlock(int id) {
    }

    @Override
    public void lockInterruptibly(int id) {
    }

    @Override
    public boolean tryLock(int id) {
        return false;
    }

    @Override
    public boolean tryLock(int id, long time, TimeUnit unit) {
        return false;
    }

    @Override
    public void unlock() {
    }
}