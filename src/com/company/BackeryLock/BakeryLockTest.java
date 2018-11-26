package com.company.BackeryLock;

import com.company.FixnumLock.AbstractFixnumLock;
import org.junit.Test;

import static org.junit.Assert.*;

public class BakeryLockTest {
    static int counter = 0;
    @Test
    public void checkBakeryLock() {
        AbstractFixnumLock lock = new BakeryLock(4);
        BakeryLockTread bakeryLockTread1 = new BakeryLockTread();
        BakeryLockTread bakeryLockTread2 = new BakeryLockTread();
        BakeryLockTread bakeryLockTread3 = new BakeryLockTread();
        BakeryLockTread bakeryLockTread4 = new BakeryLockTread();
        bakeryLockTread1.setLock(lock);
        bakeryLockTread2.setLock(lock);
        bakeryLockTread3.setLock(lock);
        bakeryLockTread4.setLock(lock);
        bakeryLockTread1.operation = 1;
        bakeryLockTread2.operation = -1;
        bakeryLockTread3.operation = 1;
        bakeryLockTread4.operation = -1;
        bakeryLockTread1.start();
        bakeryLockTread2.start();
        bakeryLockTread3.start();
        bakeryLockTread4.start();

        while(true){
            System.out.println("Counter:" + BakeryLockTread.globalCounter);
            System.out.println(bakeryLockTread1.iterations);
            System.out.println(bakeryLockTread2.iterations);
            System.out.println(bakeryLockTread3.iterations);
            System.out.println(bakeryLockTread4.iterations);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}