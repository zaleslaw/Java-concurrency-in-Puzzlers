package com.zaleslaw.concurrency.puzzlers.Puzzle_1;

/**
 * It prints "I'm main thread" only in many cases
 *
 * #2 will not be printed because all daemon thread will die after interrupting of main thread
 */
public class SimpleDaemonThread {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(()-> System.out.println("I'm alive")); // #1
        t.setDaemon(true);
        t.start();
        System.out.println("I'm main thread");                       // #2
        // Let's destroy main thread
        Thread.currentThread().interrupt();
    }
}
