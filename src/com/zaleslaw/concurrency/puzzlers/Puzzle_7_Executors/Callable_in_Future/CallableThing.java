package com.zaleslaw.concurrency.puzzlers.Puzzle_7_Executors.Callable_in_Future;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


/**
 * Easy creation of threads and reusing of threads by CachedTreadPool
 */
public class CallableThing {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        List<Future<Long>> results = new ArrayList<>();
        ExecutorService service = Executors.newCachedThreadPool();
        long sum = 0;

        for (int i = 0; i < 1_000_000; i++) {
            final int j = i;
            Future<Long> task = service.submit(() -> {
                Thread.sleep(100);
                long result = j * j * j;
                return result;
            });

            results.add(task);

        }

        for (Future<Long> result : results) {
            sum += result.get();
        }
        System.out.println("Result is " + sum);
        System.out.println("Max number of threads " + ((ThreadPoolExecutor) service).getLargestPoolSize());
    }

}
