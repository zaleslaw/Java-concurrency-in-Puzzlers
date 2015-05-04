package com.zaleslaw.concurrency.puzzlers.Puzzler_8_Synchronizers;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;


/**
 * 1 writing threads  and 100 reading threads in one time due to Semaphore object
 * <p>
 * P.S. Should synchronize resource if more writing threads are required
 */
public class SemaphoreExample {
    public static void main(String args[]) throws InterruptedException {
        Semaphore writingSemaphore = new Semaphore(1);
        Semaphore readingSemaphore = new Semaphore(100);
        List<Thread> threads = new ArrayList<>();
        List<Integer> resource = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            int j = i;
            Thread writer = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        writingSemaphore.acquire();
                        resource.add(j);
                        System.out.println("Hello from " + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        writingSemaphore.release();
                        System.out.println("Goodbye from " + Thread.currentThread().getName());
                    }

                }
            });
            writer.setName("Writer #" + i);
            threads.add(writer);
            writer.start();
        }


        for (int i = 0; i < 1000; i++) {
            int j = i;
            Thread reader = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("Hello from " + Thread.currentThread().getName());
                        readingSemaphore.acquire();
                        System.out.println("Number of elements: " + resource.size());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        readingSemaphore.release();
                        System.out.println("Goodbye from " + Thread.currentThread().getName());
                    }

                }
            });
            reader.setName("Reader #" + i);
            threads.add(reader);
            reader.start();
        }


        for (Thread t : threads) {
            t.join();
        }
        System.out.println("Result : " + resource.size());


    }

}
