package com.zaleslaw.concurrency.puzzlers.Puzzle_6_Collections.map;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * It represents HashMap object destroyed by writing from many threads
 */
public class DestroyedHashMapAndManyThreads {

    public static void main(String[] args) throws InterruptedException {


        Map<Integer, Integer> resource = new HashMap<>();
        List<Thread> threads = new ArrayList<>();


        for (int i = 0; i < 1000; i++) {
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

    }
}
