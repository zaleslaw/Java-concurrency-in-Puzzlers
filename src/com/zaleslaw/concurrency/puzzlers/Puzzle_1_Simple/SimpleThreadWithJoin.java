package com.zaleslaw.concurrency.puzzlers.Puzzle_1_Simple;

/**
 * It prints "I'm main thread" before "I'm alive", but "I'm joined main thread" after calling join() method
 *
 * Before-happens exists between #1 and #3
 */
public class SimpleThreadWithJoin {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(()-> System.out.println("I'm alive")); //#1
        t.start();
        System.out.println("I'm main thread");                       //#2
        t.join();
        System.out.println("I'm joined main thread");                //#3
    }
}
