package com.company.BackeryLock;

import com.company.FixnumLock.AbstractFixnumLock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.locks.Condition;

public class BakeryLock extends AbstractFixnumLock {
    AtomicIntegerArray tickets;
    AtomicIntegerArray entering;
    int numberOfThreads;
    public BakeryLock(int numberOfThreads){
        super(numberOfThreads);
        this.numberOfThreads = numberOfThreads;
        tickets = new AtomicIntegerArray(numberOfThreads);
        entering = new AtomicIntegerArray(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++)
        {
            tickets.set(i, 0);
            entering.set(i, 0);
        }
    }
    @Override
    public void lock(int id) {
        entering.set(id, 1);
        int max = 0;
        for (int i =0 ; i <tickets.length(); i++) {
            max = Math.max(max, tickets.get(i));
        }
        tickets.set(id, max + 1);
        entering.set(id, 0);
        //System.out.println("Thread" + Thread.currentThread().getId() + "id: " + threadId);
        /*synchronized (obj){
            for (int i = 0; i < tickets.size(); i++) {
                System.out.print(tickets.get(i) + " ");
            }
        }*/
        for (int i = 0; i < numberOfThreads; ++i)
        {
            if (i != id)
            {
                while (entering.get(i) == 1) {
                }
                while (tickets.get(i) != 0 && ( tickets.get(id) > tickets.get(i)  ||
                        (tickets.get(id) == tickets.get(i) && id > i))){
                }
            }
        }


    }



    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public void lockInterruptibly(int id) throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(int id) {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public boolean tryLock(int id, long time, TimeUnit unit) throws InterruptedException {
        return false;
    }


    public void unlock(int id) {
        tickets.set(id, 0);
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
