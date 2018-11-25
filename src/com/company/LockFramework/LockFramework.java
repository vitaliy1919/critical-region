package com.company.LockFramework;

import com.company.Counters.LockBasedCounter;
import com.company.Counters.ThreadSafeCounter;
import com.company.FixnumLock.AbstractFixnumLock;


import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

class Counter {
    volatile int value;

    public Counter(int value) {
        this.value = value;
    }

    synchronized void increase() {
        value++;
    }
}

public class LockFramework {
    HashMap<ThreadSafeCounter, String> counters = new HashMap<>();
    public void addCounterToTest(ThreadSafeCounter counter, String name) {
        counters.put(counter, name);
    }

    private void makeIteration(HashMap<ThreadSafeCounter, Double> durations, int increaseOperationsCount, int threadNumber) {
        Thread threads[] = new Thread[threadNumber];
        Iterator<Map.Entry<ThreadSafeCounter, String>> it = counters.entrySet().iterator();
        CyclicBarrier barrier = new CyclicBarrier(threadNumber);
        long start;

        final int NANO_SECONDS = 1_000_000_000;
        double duration;

        while (it.hasNext()) {
            Map.Entry<ThreadSafeCounter, String> entry = it.next();
            ThreadSafeCounter curCounter = entry.getKey();
            try {
                AbstractFixnumLock fixnumLock = null;
                if (curCounter instanceof LockBasedCounter) {
                    LockBasedCounter lockCounter = (LockBasedCounter)curCounter;
                    if (lockCounter.getLock() instanceof AbstractFixnumLock) {
                        fixnumLock = (AbstractFixnumLock)lockCounter.getLock();
                        if (fixnumLock.getNumberOfThreads() != threadNumber)
                            throw new RuntimeException(
                                    "This intance of Fixnumlock is not capable of handling " +
                                    threadNumber +
                                    " threads");
                    }
                }

                // runs threadNumber instanses of counter
                // start a barier and increases counter
                for (int i = 0; i < threadNumber ; i++)
                    threads[i] = new Thread(()->{
                        try {
                            barrier.await();
                            for (int op = 0; op < increaseOperationsCount; op++ )
                                curCounter.increase();
                        } catch (BrokenBarrierException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    });

                start = System.nanoTime();

                for (int i = 0; i < threadNumber ; i++)
                    threads[i].start();

                for (int i = 0; i < threadNumber; i++)
                    threads[i].join();

                duration = (double) (System.nanoTime() - start) / NANO_SECONDS;

                Double prevDuration = durations.get(curCounter);

                // put the sum of all durations into HashSet
                // (so that we can later calc the average)
                if (prevDuration == null)
                    durations.putIfAbsent(curCounter, duration);
                else
                    durations.put(curCounter, prevDuration + duration);

                System.out.println(entry.getValue() + ": " + duration + "s.");

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public ArrayList<LockTestInfo> test(int threadNumber, int operationCount,  int iterations)  {
        final int MAGIC_CONST = 5000;
        HashMap<ThreadSafeCounter, Double> durations = new HashMap<>();

        // calculate time for each iteration
        for (int i = 0; i < iterations; i++) {
            makeIteration(durations, operationCount, threadNumber);
        }
        Iterator<Map.Entry<ThreadSafeCounter, Double>> it = durations.entrySet().iterator();
        ArrayList<LockTestInfo> infos = new ArrayList<>();

        // iterate through durations and make statistics
        while (it.hasNext()) {
            Map.Entry<ThreadSafeCounter, Double> entry = it.next();
            entry.setValue(entry.getValue() / iterations);

            // each info contains an average duration, lock and its name
            infos.add(new LockTestInfo(entry.getKey(), counters.get(entry.getKey()), entry.getValue()));
        }

        Collections.sort(infos);

        return infos;
    }
}
