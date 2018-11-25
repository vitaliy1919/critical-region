package com.company;

import com.company.Counters.AtomicCounter;
import com.company.Counters.LockBasedCounter;
import com.company.Counters.MonitorCounter;
import com.company.LockFramework.LockFramework;
import com.company.LockFramework.LockTestInfo;
import com.company.Locks.Mutex;
import com.company.Locks.SpinLock;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// write your code here
        LockFramework framework = new LockFramework();
        framework.addCounterToTest(new AtomicCounter(1), "Atomic");
        framework.addCounterToTest(new MonitorCounter(1), "Monitor");
        framework.addCounterToTest(new LockBasedCounter(new Mutex(), 1), "Mutex");
        framework.addCounterToTest(new LockBasedCounter(new SpinLock(), 1), "Spin lock");

        ArrayList<LockTestInfo> infos = framework.test(5, 10_000_000, 1);
        System.out.println("---------------------\n----------------------\n");

        infos.forEach((info)->{
            System.out.println(info.name + ": " + info.duration);
        });
        //System.out.println("----------");


    }
}
