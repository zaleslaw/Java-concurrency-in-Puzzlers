package com.zaleslaw.concurrency.puzzlers.Puzzle_6_Collections.map.references;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * It represents WeakHashMap which clear itself when GC works
 */
public class StaticWeakHashMapAndManyThreads {

    private static StringBuffer heavyString = new StringBuffer();
    private static Map<Integer, String> resource = new WeakHashMap<>();

    public static void main(String[] args) throws InterruptedException {


        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 1_000_000; i++) {
            heavyString.append("A");
        }


        for (int i = 0; i < 1_000_000; i++) {
            final int number = i;
            final String s = String.valueOf(i) + heavyString.toString();
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(ThreadLocalRandom.current().nextInt(100));
                        resource.put(number, s);
                        System.out.println("Size is " + resource.size() + " " + s.substring(0, 1));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
            threads.add(t);
            t.setName(String.valueOf(number));
            //System.out.println("Thread # " + t.getName() + " starts");
            t.start();
        }


        for (Thread t : threads) {
            t.join();
            //System.out.println("Thread # " + t.getName() + " finished");
        }


        System.out.println("Size is " + resource.size());

    }
}
