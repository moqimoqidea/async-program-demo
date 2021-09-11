package org.Chapter5.rxjava;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;


public class AsyncRpcCall5 {


    /**
     * cost:212
     * RxSingleScheduler-1    DoneRxCachedThreadScheduler-1
     */
    public static void main(String[] args) throws InterruptedException {

        //1.
        long start = System.currentTimeMillis();
        Flowable.fromCallable(() -> {//1.1
                    Thread.sleep(1000); // 1.2模拟耗时的操作
                    return "    Done" + Thread.currentThread().getName();
                }).subscribeOn(Schedulers.io())//1.3
                .observeOn(Schedulers.single())//1.4
                .subscribe(t -> System.out.println(Thread.currentThread().getName() + t),
                        Throwable::printStackTrace);//1.5

        //2.
        System.out.println("cost:" + (System.currentTimeMillis() - start));

        //3.
        Thread.sleep(3000); // 等待流结束
    }
}
