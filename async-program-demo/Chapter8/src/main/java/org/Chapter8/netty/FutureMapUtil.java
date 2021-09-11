package org.Chapter8.netty;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class FutureMapUtil {
	// <请求id，对应的future>
    private static final ConcurrentHashMap<String, CompletableFuture<String>> futureMap = new ConcurrentHashMap<>();

    public static void put(String id, CompletableFuture<String> future) {
        futureMap.put(id, future);
    }

    public static CompletableFuture<String> remove(String id) {
        return futureMap.remove(id);
    }

}
