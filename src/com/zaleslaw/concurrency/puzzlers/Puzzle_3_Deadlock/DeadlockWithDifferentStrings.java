package com.zaleslaw.concurrency.puzzlers.Puzzle_3_Deadlock;

/**
 * Synchronization depends on caching of intern value in String type
 */
public class DeadlockWithDifferentStrings {

    public static void main(String[] args) {
        String counter1 = "a";
        String counter2 = "b";

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (counter1) {
                    System.out.println("Lock counter1 in thread " + Thread.currentThread().getId());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (counter2) {
                        System.out.println("Lock counter2 in thread " + Thread.currentThread().getId());
                    }
                }


            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (counter2) {
                    System.out.println("Lock counter2 in thread " + Thread.currentThread().getId());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (counter1) {
                        System.out.println("Lock counter1 in thread " + Thread.currentThread().getId());
                    }
                }


            }
        });


        t1.start();
        t2.start();
    }
}
