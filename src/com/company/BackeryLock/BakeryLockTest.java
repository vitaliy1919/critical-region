package com.company.BackeryLock;

import com.company.FixnumLock.AbstractFixnumLock;
import org.junit.Test;

import static org.junit.Assert.*;

public class BakeryLockTest {
    static int counter = 0;
    @Test
    public void checkBakeryLock(){
        AbstractFixnumLock lock = new BakeryLock(4);
        Thread threadOne = new Thread() {
            public void run() {
                lock.lock();
                System.out.println("Before " +  Thread.currentThread().getId()  + ": " + counter);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                counter++;
                System.out.println("After " +  Thread.currentThread().getId()  + ": " + counter);
                lock.unlock();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.lock();
                System.out.println("Before " +  Thread.currentThread().getId()  + ": " + counter);
                counter++;
                System.out.println("After " +  Thread.currentThread().getId()  + ": " + counter);
                lock.unlock();
            }
        };
        Thread threadTwo = new Thread() {
            public void run() {
                lock.lock();
                System.out.println("Before " +  Thread.currentThread().getId()  + ": " + counter);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                counter++;
                System.out.println("After " +  Thread.currentThread().getId()  + ": " + counter);
                lock.unlock();
            }
        };
        Thread threadThree = new Thread() {
            public void run() {
                lock.lock();
                System.out.println("Before " +  Thread.currentThread().getId()  + ": " + counter);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                counter--;
                System.out.println("After " +  Thread.currentThread().getId()  + ": " + counter);
                lock.unlock();
            }
        };
        Thread threadFour = new Thread() {
            public void run() {
                lock.lock();
                System.out.println("Before " +  Thread.currentThread().getId()  + ": " + counter);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                counter--;
                System.out.println("After " +  Thread.currentThread().getId()  + ": " + counter);
                lock.unlock();

                lock.lock();
                System.out.println("Before " +  Thread.currentThread().getId()  + ": " + counter);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                counter--;
                System.out.println("After " +  Thread.currentThread().getId()  + ": " + counter);
                lock.unlock();
            }
        };
        threadOne.start();
        threadTwo.start();
        threadThree.start();
        threadFour.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Counter:" + counter);
    }

}