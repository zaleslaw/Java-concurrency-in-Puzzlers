package com.zaleslaw.concurrency.puzzlers.Puzzle_1_Simple.keywrods;

/**
 * How to fix race in this class?
 */
public class PowerOfVolatile {

    static Integer a = 0, b = 23;
    static volatile int z = 0;

    public static void main(String[] args) throws InterruptedException {

        a++;
        b++;

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println("Thread1 before " + "a = " + a + " b = " + b);
                a = a * 10;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                b = b * 10;

                System.out.println("Thread1 after " + "a = " + a + " b = " + b);
            }
        });


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread2 before " + "a = " + a + " b = " + b);
                a *= 1000;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                b *= 1000;
                System.out.println("Thread2 after " + "a = " + a + " b = " + b);
            }
        });


        t1.start();
        t2.start();
        Thread.sleep(15);
        b = a;
        z = 1;
        System.out.println("Main thread before join " + "a = " + a + " b = " + b);
        t1.join();
        t2.join();
    }

}
