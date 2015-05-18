package com.zaleslaw.concurrency.puzzlers.Puzzle_8_Synchronizers;


import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Exchanger;

/**
 * Two threads exchange Message objects
 */
public class ExchangeThreadState {

    private static class Message {
        public String header;
        public String body;

        public Message(String header, String body) {
            this.header = header;
            this.body = body;
        }
    }


    private static class MyThread extends Thread {

        Exchanger<Message> exchanger;
        Queue<Message> message = new LinkedList<>();

        MyThread(Exchanger<Message> exchanger) {
            this.exchanger = exchanger;
        }

        public void run() {
            try {

                for (int i = 0; i < 10; i++) {
                    String header = this.getName() + " header " + i;
                    String body = this.getName() + " body " + i;
                    message.offer(new Message(header, body));
                    System.out.println(this.getName() + " put message: " + header);
                }


                // exchange messages
                final Message e = exchanger.exchange(message.poll());
                message.offer(e);
                System.out.println(this.getName() + " exchange message: " + e.header);
            } catch (Exception e) {
            }
        }

        public static void main(String[] args) {

            Exchanger<Message> exchanger = new Exchanger<Message>();

            Thread t1 = new MyThread(exchanger);
            Thread t2 = new MyThread(exchanger);
            t1.start();
            t2.start();
        }
    }


}
