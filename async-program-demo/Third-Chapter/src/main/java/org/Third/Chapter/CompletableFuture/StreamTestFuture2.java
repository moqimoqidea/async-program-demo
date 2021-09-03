package org.Third.Chapter.CompletableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class StreamTestFuture2 {

	public static String rpcCall(String ip, String param) {

        System.out.println(ip + " rpcCall:" + param);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return param;

    }

    /**
     * 192.168.0.3 rpcCall:192.168.0.3
     * 192.168.0.1 rpcCall:192.168.0.1
     * 192.168.0.4 rpcCall:192.168.0.4
     * 192.168.0.2 rpcCall:192.168.0.2
     * 192.168.0.7 rpcCall:192.168.0.7
     * 192.168.0.5 rpcCall:192.168.0.5
     * 192.168.0.6 rpcCall:192.168.0.6
     * 192.168.0.10 rpcCall:192.168.0.10
     * 192.168.0.8 rpcCall:192.168.0.8
     * 192.168.0.9 rpcCall:192.168.0.9
     * 192.168.0.1
     * 192.168.0.2
     * 192.168.0.3
     * 192.168.0.4
     * 192.168.0.5
     * 192.168.0.6
     * 192.168.0.7
     * 192.168.0.8
     * 192.168.0.9
     * 192.168.0.10
     * cost:2143
     */
    public static void main(String[] args) {

        // 1.生成ip列表
        List<String> ipList = new ArrayList<>();
        for (int i = 1; i <= 10; ++i) {
            ipList.add("192.168.0." + i);
        }

        // 2.并发调用
        long start = System.currentTimeMillis();
        List<CompletableFuture<String>> futureList = ipList.stream()
                .map(ip -> CompletableFuture.supplyAsync(() -> rpcCall(ip, ip)))
                .collect(Collectors.toList());

        List<String> resultList = futureList.stream()
                .map(CompletableFuture::join).collect(Collectors.toList());

        // 3.输出
        resultList.forEach(System.out::println);
        System.out.println("cost:" + (System.currentTimeMillis() - start));

    }
}
