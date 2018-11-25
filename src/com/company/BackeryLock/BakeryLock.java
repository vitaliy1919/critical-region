package com.company.BackeryLock;

import com.company.FixnumLock.AbstractFixnumLock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public class BakeryLock extends AbstractFixnumLock {
    List<Integer> tickets;
    List<Boolean> entering;
    int numberOfThreads;
    public BakeryLock(int numberOfThreads){
        super(numberOfThreads);
        this.numberOfThreads = numberOfThreads;
        tickets = new ArrayList<>(numberOfThreads);
        entering = new ArrayList<>(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++)
        {
            tickets.add(0);
            entering.add(false);
        }
    }
    @Override
    public void lock() {
        int threadId = register();
        entering.set(threadId, true);
        int max = 0;
        for (int ticket : tickets) {
            max = Math.max(max, ticket);
        }
        tickets.set(threadId, max + 1);
        entering.set(threadId, false);
        //System.out.println("Thread" + Thread.currentThread().getId() + "id: " + threadId);
        /*synchronized (obj){
            for (int i = 0; i < tickets.size(); i++) {
                System.out.print(tickets.get(i) + " ");
            }
        }*/
        for (int i = 0; i < numberOfThreads; ++i)
        {
            if (i != threadId)
            {
                while (entering.get(i)) {
                    Thread.yield();
                }
                while (tickets.get(i) != 0 && ( tickets.get(threadId) > tickets.get(i)  ||
                        (tickets.get(threadId) == tickets.get(i) && threadId > i))){
                    Thread.yield();
                }
            }
        }


    }

    @Override
    public void lock(int id) {

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
    public boolean tryLock(int id, long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        int threadId = register();
        tickets.set(threadId, 0);
        unregister();
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
