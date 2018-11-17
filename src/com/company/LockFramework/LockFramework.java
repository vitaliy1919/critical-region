package com.company.LockFramework;

import com.company.Counters.ThreadSafeCounter;

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

    private void makeIteration(HashMap<ThreadSafeCounter, Double> durations, int threadNumber) {
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
                for (int i = 0; i < threadNumber ; i++)
                    threads[i] = new Thread(()->{
                        try {
                            barrier.await();
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
    public ArrayList<LockTestInfo> test(int threadNumber, int iterations)  {
        final int MAGIC_CONST = 5000;
        HashMap<ThreadSafeCounter, Double> durations = new HashMap<>();
        for (int i = 0; i < iterations; i++) {
            makeIteration(durations, threadNumber);
        }
        Iterator<Map.Entry<ThreadSafeCounter, Double>> it = durations.entrySet().iterator();
        ArrayList<LockTestInfo> infos = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry<ThreadSafeCounter, Double> entry = it.next();
            entry.setValue(entry.getValue() / iterations);
            infos.add(new LockTestInfo(entry.getKey(), counters.get(entry.getKey()), entry.getValue()));
        }
        Collections.sort(infos);
        return infos;
//        Thread threads[] = new Thread[threadNumber];
//
//        Iterator<Map.Entry<ThreadSafeCounter, String>> it = counters.entrySet().iterator();
//        CyclicBarrier barrier = new CyclicBarrier(threadNumber);
//        long start;
//        final int NANO_SECONDS = 1_000_000_000;
//        double duration;
//        while (it.hasNext()) {
//            Map.Entry<ThreadSafeCounter, String> entry = it.next();
//            try {
//                for (int i = 0; i < threadNumber ; i++)
//                    threads[i] = new Thread(()->{
//                        try {
//                            barrier.await();
//                            entry.getKey().increase();
//                        } catch (BrokenBarrierException | InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    });
//                start = System.nanoTime();
//                for (int i = 0; i < threadNumber ; i++)
//                    threads[i].start();
//
//                for (int i = 0; i < threadNumber; i++)
//                    threads[i].join();
//                duration = (double) (System.nanoTime() - start) / NANO_SECONDS;
//                System.out.println(entry.getValue() + ": " + duration + "s.");
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//        final AtomicInteger aI = new AtomicInteger(0);
//        final Counter counter1 = new Counter(1);
//        CyclicBarrier barrier = new CyclicBarrier(MAGIC_CONST);
//        for (int i = 0; i < MAGIC_CONST ; i++)
//            threads[i] = new Thread(()->{
//                try {
//                    barrier.await();
//                } catch (BrokenBarrierException | InterruptedException e) {
//                    e.printStackTrace();
//                }
//                aI.incrementAndGet();
//
//            });
//        long timeStart = System.nanoTime();
//        for (int i = 0; i < MAGIC_CONST ; i++)
//            threads[i].start();
//        try {
//            for (int i = 0; i < MAGIC_CONST; i++)
//                threads[i].join();
//        } catch (Exception e){
//
//        }
//        long duration = System.nanoTime() - timeStart;
//        System.out.println("Atomic integer took " + duration / 1_000_000_000d +"s.");
//        Semaphore semaphore = new Semaphore(1);
//        int counter[] = new int[1];
//        for (int i = 0; i < MAGIC_CONST ; i++)
//            threads[i] = new Thread(()->{
//                try {
//                    try {
//                        barrier.await();
//                    } catch (BrokenBarrierException | InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                semaphore.acquire();
//                counter[0]++;
//                semaphore.release();
//                } catch (Exception e) {
//
//                }
//            });
//         timeStart = System.nanoTime();
//        for (int i = 0; i < MAGIC_CONST ; i++)
//            threads[i].start();
//        try {
//            for (int i = 0; i < MAGIC_CONST; i++)
//                threads[i].join();
//        } catch (Exception e){
//
//        }
//        duration = System.nanoTime() - timeStart;
//        System.out.println("Mutex took " + duration / 1_000_000_000d +"s.");
//
//        for (int i = 0; i < MAGIC_CONST ; i++)
//            threads[i] = new Thread(()->{
//                try {
//                    try {
//                        barrier.await();
//                    } catch (BrokenBarrierException | InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    counter1.increase();
//
//                } catch (Exception e) {
//
//                }
//            });
//        timeStart = System.nanoTime();
//        for (int i = 0; i < MAGIC_CONST ; i++)
//            threads[i].start();
//        try {
//            for (int i = 0; i < MAGIC_CONST; i++)
//                threads[i].join();
//        } catch (Exception e){
//
//        }
//        duration = System.nanoTime() - timeStart;
//        System.out.println("Monitor took " + duration / 1_000_000_000d +"s.");
    }
}
