package com.zaleslaw.concurrency.puzzlers.Puzzle_7_Executors.Callable_in_Future;


import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BabyShootMeDown {


    private static class Worker extends Thread {
        public void run() {
            try {
                System.out.println(this.getName() + " started: " + new Date());
                Thread.sleep(500);
                System.out.println(this.getName() + " finished:" + new Date());
            } catch (Exception e) {
            }
        }

        public static void main(String[] args) {

            ExecutorService service = Executors.newFixedThreadPool(2);

            for (int i = 0; i < 10; i++) {
                Worker worker = new Worker();
                service.execute(worker);
            }

            service.shutdown();
        }
    }


}
