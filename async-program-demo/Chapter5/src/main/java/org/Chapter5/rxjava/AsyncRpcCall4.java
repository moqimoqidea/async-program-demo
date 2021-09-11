package org.Chapter5.rxjava;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

//

public class AsyncRpcCall4 {

	private static String rpcCall(String ip, String param) {
		System.out.println(Thread.currentThread().getName() + " " + ip + " rpcCall: " + param);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return param;
	}

    /**
     * main execute over and wait
     * RxCachedThreadScheduler-1 192.168.0.1 rpcCall: 192.168.0.1
     * 192.168.0.1
     * RxCachedThreadScheduler-1 192.168.0.2 rpcCall: 192.168.0.2
     * 192.168.0.2
     * RxCachedThreadScheduler-1 192.168.0.3 rpcCall: 192.168.0.3
     * 192.168.0.3
     * RxCachedThreadScheduler-1 192.168.0.4 rpcCall: 192.168.0.4
     * 192.168.0.4
     * RxCachedThreadScheduler-1 192.168.0.5 rpcCall: 192.168.0.5
     * 192.168.0.5
     * RxCachedThreadScheduler-1 192.168.0.6 rpcCall: 192.168.0.6
     * 192.168.0.6
     * RxCachedThreadScheduler-1 192.168.0.7 rpcCall: 192.168.0.7
     * 192.168.0.7
     * RxCachedThreadScheduler-1 192.168.0.8 rpcCall: 192.168.0.8
     * 192.168.0.8
     * RxCachedThreadScheduler-1 192.168.0.9 rpcCall: 192.168.0.9
     * 192.168.0.9
     * RxCachedThreadScheduler-1 192.168.0.10 rpcCall: 192.168.0.10
     * 192.168.0.10
     */
	public static void main(String[] args) throws InterruptedException {

		// 1.生成ip列表
		List<String> ipList = new ArrayList<>();
		for (int i = 1; i <= 10; ++i) {
			ipList.add("192.168.0." + i);
		}

		// 2.顺序调用
		Flowable.fromArray(ipList.toArray(new String[0])).observeOn(Schedulers.io())// 2.1切换到IO线程执行
				.map(v -> rpcCall(v, v))// 2.2映射结果
				.subscribe(System.out::println);// 2.3订阅

		//3.
		System.out.println("main execute over and wait");
		Thread.currentThread().join();// 挂起main函数所在线程
	}
}
