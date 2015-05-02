package com.zaleslaw.concurrency.puzzlers.Puzzle_6_Collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * It represents LinkedList object destroyed by writing from many threads
 */
public class DestroyedLinkedListAndManyThreads {

    public static void main(String[] args) throws InterruptedException {
        List<Integer> resource = new LinkedList<>();
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

        System.out.println("Size is " + resource.size());

        Iterator<Integer> iter = resource.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }

    }
}
