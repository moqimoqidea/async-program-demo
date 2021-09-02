package org.Second.Chapter.ThreadPool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AsyncThreadPoolExample3 {

	public static String doSomethingA() {

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("--- doSomethingA---");
		return "A Task Done";
	}

	// 0自定义线程池
	private final static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
	private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(AVAILABLE_PROCESSORS,
			AVAILABLE_PROCESSORS * 2, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(5),
			new NamedThreadFactory("ASYNC-POOL"), new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * --- doSomethingA---
     * A Task Done
     */
	public static void main(String[] args) throws InterruptedException, ExecutionException {

		// 1.开启异步单元执行任务A
		Future<String> resultA = POOL_EXECUTOR.submit(AsyncThreadPoolExample3::doSomethingA);

		// 2.同步等待执行结果
		System.out.println(resultA.get());
	}
}
