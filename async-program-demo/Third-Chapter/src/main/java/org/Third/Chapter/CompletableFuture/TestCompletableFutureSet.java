package org.Third.Chapter.CompletableFuture;

import java.util.concurrent.*;

public class TestCompletableFutureSet {
    // 0自定义线程池
    private final static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(AVAILABLE_PROCESSORS,
            AVAILABLE_PROCESSORS * 2, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(5),
            new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * ---main thread wait future result---
     * ----pool-1-thread-1 set future result----
     * hello,jiaduo
     * ---main thread got future result---
     * t = hello,jiaduo
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {

        // 1.创建一个CompletableFuture对象
        CompletableFuture<String> future = new CompletableFuture<>();

        // 2.开启线程计算任务结果，并设置
        POOL_EXECUTOR.execute(() -> {

            // 2.1休眠3s，模拟任务计算
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 2.2设置计算结果到future
            System.out.println("----" + Thread.currentThread().getName() + " set future result----");
            future.complete("hello,jiaduo");

        });

        // 3.等待计算结果
        System.out.println("---main thread wait future result---");
        System.out.println(future.get());
        // System.out.println(future.get(1000,TimeUnit.MILLISECONDS));
        System.out.println("---main thread got future result---");

        future.whenComplete((t, u) -> {

            if (null == u) {
                System.out.println("t = " + t);
            } else {
                System.out.println(u.getLocalizedMessage());

            }
        });

        POOL_EXECUTOR.shutdown();
    }
}
