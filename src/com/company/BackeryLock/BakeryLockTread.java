package com.company.BackeryLock;

import com.company.FixnumLock.AbstractFixnumLock;

public class BakeryLockTread extends Thread {

    public long iterations = 0;
    private AbstractFixnumLock bakeryLock;
    public static int globalCounter;
    public int operation;
    @Override
    public void run() {
        int id = bakeryLock.register();
        if(id != -1) {
            while(true) {
                bakeryLock.lock();
                iterations++;
                globalCounter += operation;
                bakeryLock.unlock();
            }
        }
    }
    public void setLock(AbstractFixnumLock bakeryLock){
        this.bakeryLock = bakeryLock;
    }
}
