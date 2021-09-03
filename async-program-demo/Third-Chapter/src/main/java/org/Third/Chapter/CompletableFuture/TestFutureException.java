package org.Third.Chapter.CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class TestFutureException {

    /**
     * one start ------------
     * ---main thread wait future result---
     * ----thread-1 set future result----
     * Exception in thread "main" java.util.concurrent.ExecutionException: java.lang.RuntimeException: error exception
     * at java.util.concurrent.CompletableFuture.reportGet(CompletableFuture.java:357)
     * at java.util.concurrent.CompletableFuture.get(CompletableFuture.java:1908)
     * at org.Third.Chapter.CompletableFuture.TestFutureException.one(TestFutureException.java:31)
     * at org.Third.Chapter.CompletableFuture.TestFutureException.main(TestFutureException.java:98)
     * Caused by: java.lang.RuntimeException: error exception
     * at org.Third.Chapter.CompletableFuture.TestFutureException.lambda$one$0(TestFutureException.java:25)
     * at java.lang.Thread.run(Thread.java:748)
     */
    public static void one() throws InterruptedException, ExecutionException {
        System.out.println("one start ------------");
        // 1.创建一个CompletableFuture对象
        CompletableFuture<String> future = new CompletableFuture<>();

        // 2.开启线程计算任务结果，并设置
        new Thread(() -> {

            // 2.1休眠3s，模拟任务计算
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 2.2设置计算结果到future
            System.out.println("----" + Thread.currentThread().getName() + " set future result----");
            future.completeExceptionally(new RuntimeException("error exception"));

        }, "thread-1").start();

        // 3.等待计算结果
        System.out.println("---main thread wait future result---");
        System.out.println(future.get());
        // System.out.println(future.get(1000,TimeUnit.MILLISECONDS));
        System.out.println("---main thread got future result---");
        System.out.println("one end ------------");
        System.out.println();
    }

    /**
     * two start ------------
     * ---main thread wait future result---
     * ----thread-1 set future result----
     * default
     * ---main thread got future result---
     * two end ------------
     */
    public static void two() throws InterruptedException, ExecutionException {
        System.out.println("two start ------------");
        // 1.创建一个CompletableFuture对象
        CompletableFuture<String> future = new CompletableFuture<>();

        // 2.开启线程计算任务结果，并设置
        new Thread(() -> {

            // 2.1休眠3s，模拟任务计算
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 2.2设置计算结果到future
            System.out.println("----" + Thread.currentThread().getName() + " set future result----");
            future.completeExceptionally(new RuntimeException("error exception"));

        }, "thread-1").start();
        ;
        // 3.等待计算结果
        System.out.println("---main thread wait future result---");
        System.out.println(future.exceptionally(t -> "default").get());// 默认值
        // System.out.println(future.get(1000,TimeUnit.MILLISECONDS));
        System.out.println("---main thread got future result---");
        System.out.println("two end ------------");
        System.out.println();
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        // one();

        two();
    }
}
