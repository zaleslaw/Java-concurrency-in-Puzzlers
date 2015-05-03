package com.zaleslaw.concurrency.puzzlers.Puzzle_6_Collections.map;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * It represents HashMap object destroyed by writing from many threads
 */
public class SynchronizedHashMapAndManyThreads {

    public static void main(String[] args) throws InterruptedException {

        long start = System.nanoTime();

        Map<Integer, Integer> resource = Collections.synchronizedMap(new HashMap<>());
        List<Thread> threads = new ArrayList<>();


        for (int i = 0; i < 10000; i++) {
            final int number = i;
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(ThreadLocalRandom.current().nextInt(100));
                        resource.put(number, number);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
            threads.add(t);
            t.setName(String.valueOf(number));
            System.out.println("Thread # " + t.getName() + " starts");
            t.start();
        }


        for (Thread t : threads) {
            t.join();
            System.out.println("Thread # " + t.getName() + " finished");
        }


        Iterator<Map.Entry<Integer, Integer>> iter = resource.entrySet().iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next().getKey());
        }

        System.out.println("Size is " + resource.size());
        System.out.println("Result time is " + (System.nanoTime() - start) / 1000000);
    }
}
