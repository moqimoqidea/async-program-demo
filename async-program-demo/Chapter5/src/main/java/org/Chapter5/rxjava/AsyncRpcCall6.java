package org.Chapter5.rxjava;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class AsyncRpcCall6 {

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
     * 192.168.0.4 rpcCall:192.168.0.4
     * 192.168.0.3 rpcCall:192.168.0.3
     * 192.168.0.5 rpcCall:192.168.0.5
     * 192.168.0.2 rpcCall:192.168.0.2
     * 192.168.0.1 rpcCall:192.168.0.1
     * 192.168.0.6 rpcCall:192.168.0.6
     * 192.168.0.8 rpcCall:192.168.0.8
     * 192.168.0.7 rpcCall:192.168.0.7
     * 192.168.0.9 rpcCall:192.168.0.9
     * 192.168.0.10 rpcCall:192.168.0.10
     * RxCachedThreadScheduler-2 192.168.0.2
     * RxCachedThreadScheduler-10 192.168.0.3
     * RxCachedThreadScheduler-10 192.168.0.4
     * RxCachedThreadScheduler-10 192.168.0.5
     * RxCachedThreadScheduler-10 192.168.0.6
     * RxCachedThreadScheduler-10 192.168.0.7
     * RxCachedThreadScheduler-10 192.168.0.8
     * RxCachedThreadScheduler-10 192.168.0.9
     * RxCachedThreadScheduler-10 192.168.0.10
     * RxCachedThreadScheduler-10 192.168.0.1
     */
    public static void main(String[] args) throws InterruptedException {

        // 1.生成ip列表
        List<String> ipList = new ArrayList<>();
        for (int i = 1; i <= 10; ++i) {
            ipList.add("192.168.0." + i);
        }

        // 2.并发调用
        Flowable.fromArray(ipList.toArray(new String[0]))
                .flatMap(ip -> //2.1
                        Flowable.just(ip)//2.2
                                .subscribeOn(Schedulers.io())//2.3
                                .map(v -> rpcCall(v, v)))//2.4
                .subscribe(t -> System.out.println(Thread.currentThread().getName() + " " + t));

        Thread.sleep(3000);
    }

}
