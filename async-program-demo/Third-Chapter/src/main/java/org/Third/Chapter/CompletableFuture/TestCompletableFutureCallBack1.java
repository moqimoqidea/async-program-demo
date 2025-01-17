package org.Third.Chapter.CompletableFuture;

import java.util.concurrent.*;
import java.util.function.Consumer;

//又返回结果的回调
public class TestCompletableFutureCallBack1 {
    // 0.创建线程池
    private static final ThreadPoolExecutor bizPoolExecutor =
            new ThreadPoolExecutor(8, 8, 1, TimeUnit.MINUTES,
                    new LinkedBlockingQueue<>(10));

	// I thenRun不能访问oneFuture的结果
	public static void thenRun() throws InterruptedException, ExecutionException {
        System.out.println("thenRun start --------------");

		// 1.创建异步任务，并返回future
		CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(() -> {

            // 1.1休眠2s，模拟任务计算
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 1.2返回计算结果
            return "hello";
        });
		// 2.在future上施加事件，当future计算完成后回调该事件，并返回新future
		CompletableFuture<Void> twoFuture = oneFuture.thenRun(() -> {
            // 2.1.1当oneFuture任务计算完成后做一件事情
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
            System.out.println("---after oneFuture over doSomething---");
        });

		// 3.同步等待twoFuture对应的任务完成，返回结果固定为null
		System.out.println(twoFuture.get());

        System.out.println("thenRun end --------------");
        System.out.println();
	}

	// II thenRun不能访问oneFuture的结果
	public static void thenAccept() throws InterruptedException, ExecutionException {
        System.out.println("thenAccept start --------------");

		// 1.创建异步任务，并返回future
		CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(() -> {

            // 1.1休眠2s，模拟任务计算
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 1.2返回计算结果
            return "hello";
        });
		// 2.在future上施加事件，当future计算完成后回调该事件，并返回新future
		CompletableFuture<Void> twoFuture = oneFuture.thenAccept(t -> {
            // 2.1.1对oneFuture返回的结果进行加工
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("---after oneFuture over doSomething---" + t);
        });

		// 3.在future上施加事件，当future计算完成后回调该事件，并返回新future
		CompletableFuture<Void> threeFuture = oneFuture.thenAccept(t -> {
            // 2.1.1对oneFuture返回的结果进行加工
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("---after oneFuture over doSomething---" + t);
        });

		// 3.同步等待twoFuture对应的任务完成，返回结果固定为null
		System.out.println(twoFuture.get());

        System.out.println("thenAccept end --------------");
        System.out.println();
	}

	// III thenRun不能访问oneFuture的结果
	public static void thenRunAsync() throws InterruptedException, ExecutionException {
        System.out.println("thenRunAsync start --------------");

		// 1.创建异步任务，并返回future
		CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(() -> {

            // 1.1休眠2s，模拟任务计算
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 1.2返回计算结果
            return "hello";
        });
		// 2.在future上施加事件，当future计算完成后回调该事件，并返回新future
		CompletableFuture<Void> twoFuture = oneFuture.thenRunAsync(() -> {
            // 2.1.1当oneFuture任务计算完成后做一件事情
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("---after oneFuture over doSomething---");
        });

		// 3.同步等待twoFuture对应的任务完成，返回结果固定为null
		System.out.println(twoFuture.get());
        System.out.println("thenRunAsync end --------------");
        System.out.println();
	}

	// IV thenRun不能访问oneFuture的结果
	public static void thenAcceptAsync() throws InterruptedException, ExecutionException {
        System.out.println("thenAcceptAsync start --------------");
		// 1.创建异步任务，并返回future
		CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(() -> {

            // 1.1休眠2s，模拟任务计算
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 1.2返回计算结果
            return "hello";
        });
		// 2.在future上施加事件，当future计算完成后回调该事件，并返回新future
		CompletableFuture<Void> twoFuture = oneFuture.thenAcceptAsync(t -> {
            // 2.1.1对oneFuture返回的结果进行加工
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("---after oneFuture over doSomething---" + t);
        });

		// 3.同步等待twoFuture对应的任务完成，返回结果固定为null
		System.out.println(twoFuture.get());
        System.out.println("thenAcceptAsync end --------------");
        System.out.println();
	}

    /**
     * thenRun start --------------
     * ForkJoinPool.commonPool-worker-1
     * ---after oneFuture over doSomething---
     * null
     * thenRun end --------------
     *
     * thenAccept start --------------
     * ---after oneFuture over doSomething---hello
     * ---after oneFuture over doSomething---hello
     * null
     * thenAccept end --------------
     *
     * thenRunAsync start --------------
     * ---after oneFuture over doSomething---
     * null
     * thenRunAsync end --------------
     *
     * thenAcceptAsync start --------------
     * ---after oneFuture over doSomething---hello
     * null
     * thenAcceptAsync end --------------
     */
	public static void main(String[] args) throws InterruptedException, ExecutionException {

		// 1.thenRun test
		thenRun();

		// 2.thenAccept test
		thenAccept();

		// 3.thenRunAsync test
		thenRunAsync();

		// 4.thenAcceptAsync test;
		thenAcceptAsync();
	}

}
