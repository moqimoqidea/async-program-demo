package org.Chapter5.rxjava;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class AsyncRpcCallRomFuture {

    public static String rpcCall(String ip, String param) {

        System.out.println(ip + " rpcCall:" + param);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return param;

    }

    /**
     * 192.168.0.6 rpcCall:192.168.0.6
     * 192.168.0.7 rpcCall:192.168.0.7
     * 192.168.0.5 rpcCall:192.168.0.5
     * 192.168.0.3 rpcCall:192.168.0.3
     * 192.168.0.1 rpcCall:192.168.0.1
     * 192.168.0.4 rpcCall:192.168.0.4
     * 192.168.0.9 rpcCall:192.168.0.9
     * 192.168.0.2 rpcCall:192.168.0.2
     * 192.168.0.8 rpcCall:192.168.0.8
     * 192.168.0.10 rpcCall:192.168.0.10
     * 192.168.0.1
     * 192.168.0.3
     * 192.168.0.4
     * 192.168.0.5
     * 192.168.0.6
     * 192.168.0.7
     * 192.168.0.9
     * 192.168.0.2
     * 192.168.0.8
     * 192.168.0.10
     *
     * cost:2210
     */
    public static void main(String[] args) {

        // 1.生成ip列表
        List<String> ipList = new ArrayList<>();
        for (int i = 1; i <= 10; ++i) {
            ipList.add("192.168.0." + i);
        }

        // 2.并发调用
        long start = System.currentTimeMillis();
        Flowable.fromArray( ipList.toArray(new String[0]))
                .flatMap(ip -> //2.1
                        Flowable.just(ip)//2.2
                                .subscribeOn(Schedulers.io())//2.3
                                .map(v -> rpcCall(v, v)))//2.4
                .blockingSubscribe(System.out::println);//2.5

        System.out.println();
        //3.打印耗时
        System.out.println("cost:" + (System.currentTimeMillis() - start));
    }

}
