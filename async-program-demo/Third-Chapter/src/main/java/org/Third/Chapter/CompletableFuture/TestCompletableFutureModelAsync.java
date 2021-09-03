package org.Third.Chapter.CompletableFuture;

import java.util.concurrent.*;

public class TestCompletableFutureModelAsync {
    // 0自定义线程池
    private final static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(AVAILABLE_PROCESSORS,
            AVAILABLE_PROCESSORS * 2, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(5),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public static CompletableFuture<String> runAsync(Runnable runnable) {
        CompletableFuture<String> future = new CompletableFuture<>();

        // 2.开启线程计算任务结果，并设置
        POOL_EXECUTOR.execute(() -> {

            // 2.1休眠3s，模拟任务计算
            try {
                runnable.run();
                future.complete(null);

            } catch (Exception e) {
				future.completeExceptionally(e);
			}
		});

		return future;
	}

    /**
     * hello
     * t = null
     * ------------------------hang------------------------
     */
	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {

		// 1.创建一个CompletableFuture对象
        CompletableFuture<String> future = runAsync(() -> {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("hello");

        });

        future.whenComplete((t, u) -> {

            if (null == u) {
                System.out.println("t = " + t);
            } else {
                System.out.println(u.getLocalizedMessage());
            }
        });

	}
}
