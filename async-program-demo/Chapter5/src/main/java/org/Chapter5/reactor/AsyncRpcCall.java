package org.Chapter5.reactor;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class AsyncRpcCall {

	private static String rpcCall(String ip, String param) {

		System.out.println(ip + " rpcCall:" + param);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return param;

    }

    /**
     * 192.168.0.1 rpcCall:192.168.0.1
     * 192.168.0.3 rpcCall:192.168.0.3
     * 192.168.0.2 rpcCall:192.168.0.2
     * 192.168.0.4 rpcCall:192.168.0.4
     * 192.168.0.5 rpcCall:192.168.0.5
     * 192.168.0.6 rpcCall:192.168.0.6
     * 192.168.0.7 rpcCall:192.168.0.7
     * 192.168.0.8 rpcCall:192.168.0.8
     * 192.168.0.9 rpcCall:192.168.0.9
     *
     * cost = 260
     * 192.168.0.10 rpcCall:192.168.0.10
     * 192.168.0.1
     * 192.168.0.7
     * 192.168.0.10
     * 192.168.0.2
     * 192.168.0.3
     * 192.168.0.4
     * 192.168.0.5
     * 192.168.0.6
     * 192.168.0.8
     * 192.168.0.9
     */
    public static void main(String[] args) throws InterruptedException {

        // 1.生成ip列表
        List<String> ipList = new ArrayList<>();
        for (int i = 1; i <= 10; ++i) {
            ipList.add("192.168.0." + i);
        }

        long start = System.currentTimeMillis();
        // 2.并发调用
        Flux.fromArray(ipList.toArray(new String[0])).flatMap(ip -> // 2.1
                        Flux.just(ip)// 2.2
                                .subscribeOn(Schedulers.boundedElastic())// 2.3
                                .map(v -> rpcCall(v, v)))// 2.4
                .subscribe(System.out::println);

        System.out.println();
        System.out.println("cost = " + (System.currentTimeMillis() - start));

        Thread.sleep(3000);
    }

}
