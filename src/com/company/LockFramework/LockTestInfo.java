package com.company.LockFramework;

import com.company.Counters.ThreadSafeCounter;

public class LockTestInfo implements Comparable<LockTestInfo>{
    public ThreadSafeCounter counter;
    public String name;
    public double duration;

    public LockTestInfo(ThreadSafeCounter counter, String name, double duration) {
        this.counter = counter;
        this.name = name;
        this.duration = duration;
    }

    @Override
    public int compareTo(LockTestInfo lockTestInfo) {
        return Double.compare(duration, lockTestInfo.duration);
    }
}
