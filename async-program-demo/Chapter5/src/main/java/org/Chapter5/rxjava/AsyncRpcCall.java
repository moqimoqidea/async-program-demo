package org.Chapter5.rxjava;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.CompletableFuture;

public class AsyncRpcCall {

    /**
     * disposed = false
     * message from main thread.
     * message from main future async thread.
     */
    public static void main(String[] args) throws InterruptedException {

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "message from main future async thread.";
        });

        Disposable subscribe = Flowable.fromCallable(future::get)
                .subscribeOn(Schedulers.io()).subscribe(System.out::println);
        boolean disposed = subscribe.isDisposed();
        System.out.println("disposed = " + disposed);
        System.out.println("message from main thread.");

        Thread.sleep(3000);
    }

}
