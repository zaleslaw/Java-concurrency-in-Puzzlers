package com.zaleslaw.concurrency.puzzlers.Puzzle_6_Collections.linkedlist;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The synchronized LinkedList has no destroyed state
 */
public class SynchronizedLinkedListAndManyThreads {

    public static void main(String[] args) throws InterruptedException {

        long start = System.nanoTime();

        List<Integer> resource = Collections.synchronizedList(new LinkedList<Integer>());
        List<Thread> threads = new ArrayList<>();


        for (int i = 0; i < 1000; i++) {
            final int number = i;
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(ThreadLocalRandom.current().nextInt(100));
                        resource.add(number);
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

        Iterator<Integer> iter = resource.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }


        System.out.println("Size is " + resource.size());
        System.out.println("Result time is " + (System.nanoTime() - start));

    }
}
