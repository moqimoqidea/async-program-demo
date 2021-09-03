package org.Third.Chapter.CompletableFuture;

import java.util.concurrent.*;

public class TestCompletableFutureRunAsync {

    // 0.创建线程池
    private static final ThreadPoolExecutor bizPoolExecutor =
            new ThreadPoolExecutor(8, 8, 1, TimeUnit.MINUTES,
                    new LinkedBlockingQueue<>(10));

    // 1. 没有返回值的异步执行
    public static void runAsync() throws InterruptedException, ExecutionException {
        System.out.println("runAsync start ------------");
        // 1.1创建异步任务，并返回future
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            // 1.1.1休眠2s模拟任务计算
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("over");
        });

        // 1.2 同步等待异步任务执行结束
        System.out.println(future.get());
        System.out.println("runAsync end ------------");
        System.out.println();
    }

    // 2. 有返回值的异步执行
    public static void supplyAsync() throws InterruptedException, ExecutionException {
        System.out.println("supplyAsync start ------------");
        // 2.1创建异步任务，并返回future
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            // 2.1.1休眠2s模拟任务计算
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 2.1.2 返回异步计算结果
            return "hello,jiaduo";
        });

        // 2.2 同步等待异步任务执行结束
        System.out.println(future.get());
        System.out.println("supplyAsync end ------------");
        System.out.println();
    }

    // 3. 没有返回值的异步执行，异步任务有业务自己线程池执行
    public static void runAsyncWithBizExecutor() throws InterruptedException, ExecutionException {
        System.out.println("runAsyncWithBizExecutor start ------------");
        // 1.1创建异步任务，并返回future
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            // 1.1.1休眠2s模拟任务计算
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("over");
        }, bizPoolExecutor);

        // 1.2 同步等待异步任务执行结束
        System.out.println(future.get());
        System.out.println("runAsyncWithBizExecutor end ------------");
        System.out.println();
    }

    // 4. 有返回值的异步执行
    public static void supplyAsyncWithBizExecutor() throws InterruptedException, ExecutionException {
        System.out.println("supplyAsyncWithBizExecutor start ------------");
        // 2.1创建异步任务，并返回future
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            // 2.1.1休眠2s模拟任务计算
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 2.1.2 返回异步计算结果
            return "hello,jiaduo";
        }, bizPoolExecutor);

        // 2.2 同步等待异步任务执行结束
        System.out.println(future.get());
        System.out.println("supplyAsyncWithBizExecutor end ------------");
        System.out.println();
    }

    /**
     * runAsync start ------------
     * over
     * null
     * runAsync end ------------
     *
     * supplyAsync start ------------
     * hello,jiaduo
     * supplyAsync end ------------
     *
     * runAsyncWithBizExecutor start ------------
     * over
     * null
     * runAsyncWithBizExecutor end ------------
     *
     * supplyAsyncWithBizExecutor start ------------
     * hello,jiaduo
     * supplyAsyncWithBizExecutor end ------------
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        // 1 runAsync
        runAsync();

        // 2. supplyAsync
        supplyAsync();

        // 3.runAsyncWithBizExecutor
        runAsyncWithBizExecutor();

        // 4. supplyAsyncWithBizExecutor
        supplyAsyncWithBizExecutor();

        bizPoolExecutor.shutdown();
    }

}
