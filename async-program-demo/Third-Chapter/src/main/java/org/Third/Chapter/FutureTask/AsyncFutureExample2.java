package org.Third.Chapter.FutureTask;

import java.util.concurrent.*;

/**
 * Hello world!
 *
 */
public class AsyncFutureExample2 {

	public static String doSomethingA() {

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("--- doSomethingA---");

		return "TaskAResult";
	}

    // 0自定义线程池
    private final static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(AVAILABLE_PROCESSORS,
            AVAILABLE_PROCESSORS * 2, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(5),
            new ThreadPoolExecutor.CallerRunsPolicy());

	public static String doSomethingB() {
		try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("--- doSomethingB---");
        return "TaskBResult";

    }

    /**
     * --- doSomethingB---
     * --- doSomethingA---
     * TaskAResult TaskBResult
     * 2113
     * ------------------------hang------------------------
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        long start = System.currentTimeMillis();

        // 1.创建future任务
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            try {
                return doSomethingA();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });

        // 2.开启异步单元执行任务A
        POOL_EXECUTOR.execute(futureTask);

        // 3.执行任务B
		String taskBResult = doSomethingB();

		// 4.同步等待线程A运行结束
		String taskAResult = futureTask.get();
		// 5.打印两个任务执行结果
		System.out.println(taskAResult + " " + taskBResult);
		System.out.println(System.currentTimeMillis() - start);
	}
}
