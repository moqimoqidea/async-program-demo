package org.ChapterIV.Async;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CompletableFuture;

public class TestTaskExecutor {

    /**
     * main begin
     * Sep 11, 2021 8:52:13 PM org.springframework.aop.interceptor.AsyncExecutionAspectSupport getDefaultExecutor
     * INFO: No task executor bean found for async processing: no bean of type TaskExecutor and no bean named 'taskExecutor' either
     * main end
     * SimpleAsyncTaskExecutor-1doSomething
     * SimpleAsyncTaskExecutor-1 done
     */
    public static void main(String[] arg) {
        // 1.创建容器上下文
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                new String[]{"beans-annotation.xml"});

        // 2. 获取AsyncExecutorExample实例并调用打印方法
        System.out.println(Thread.currentThread().getName() + " begin ");
        AsyncAnnotationExample asyncCommentExample = applicationContext.getBean(AsyncAnnotationExample.class);

        // 3.获取异步future并设置回调
        CompletableFuture<String> resultFuture = asyncCommentExample.doSomething();
        resultFuture.whenComplete((t, u) -> {
            if (null == u) {
                System.out.println(Thread.currentThread().getName() + " " + t);
            } else {
                System.out.println("error:" + u.getLocalizedMessage());
            }

        });

        System.out.println(Thread.currentThread().getName() + " end ");
    }

}
