package com.zaleslaw.concurrency.puzzlers.Puzzle_1;

/**
 * It prints "I'm main thread" before "I'm alive"
 *
 * Before-happens doesn't exist between #1 and #2
 */
public class SimpleThread {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(()-> System.out.println("I'm alive")); // #1
        t.start();
        System.out.println("I'm main thread");                       // #2

        // Let's destroy main thread
        Thread.currentThread().interrupt();
    }
}
