package com.zaleslaw.concurrency.puzzlers.Puzzle_6_Collections.queue;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;


public class BlockingQueueWithFixedSize {
    public static void main(String[] args) throws InterruptedException {
        final BlockingQueue<Message> q = new ArrayBlockingQueue<>(100);
        final List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 20; i++) {

            Integer j = i;

            Thread producer = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (; ; ) {
                        if (Thread.currentThread().isInterrupted()) break;
                        Message m = new Message("Header" + j, "Body" + j);
                        if (q.offer(m)) {
                            System.out.println("Producer # " + j + " put " + m.header + " " + m.body);
                        } else {
                            System.out.println("Producer # " + j + " lost message");
                        }

                        try {
                            Thread.sleep(ThreadLocalRandom.current().nextInt(100));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.println("Thread # " + Thread.currentThread().getName() + " was interrupted");
                }
            });


            threads.add(producer);
            producer.setName("Producer # " + j);
            producer.start();
        }


        for (int i = 0; i < 10; i++) {

            Integer j = i;

            Thread consumer = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!Thread.currentThread().isInterrupted()) {

                        if (q.size() > 0) {
                            Message m = q.poll();
                            if (m.header == null || m.body == null) {
                                System.out.println("Element was corrupted");
                            }
                        }

                        System.out.println("Size is " + q.size());


                        try {
                            Thread.sleep(ThreadLocalRandom.current().nextInt(100));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }

                    System.out.println("Thread # " + Thread.currentThread().getName() + " was interrupted");
                }
            });

            threads.add(consumer);
            consumer.setName("Consumer # " + j);
            consumer.start();
        }


        Thread.sleep(10000);

        for (Thread t : threads) {
            t.interrupt();
        }

        for (Thread t : threads) {
            t.join();
            System.out.println("Thread # " + t.getName() + " finished");
        }


    }

    private static class Message {
        public String header;
        public String body;

        public Message(String header, String body) {
            this.header = header;
            this.body = body;
        }
    }

}

