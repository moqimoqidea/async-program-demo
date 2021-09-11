package org.ChapterIV.Async;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestTaskExecutorException {

    /**
     * main begin
     * Sep 11, 2021 8:51:22 PM org.springframework.aop.interceptor.AsyncExecutionAspectSupport getDefaultExecutor
     * INFO: No task executor bean found for async processing: no bean of type TaskExecutor and no bean named 'taskExecutor' either
     * main end
     * Sep 11, 2021 8:51:22 PM org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler handleUncaughtException
     * SEVERE: Unexpected exception occurred invoking async method: public void org.ChapterIV.Async.AsyncAnnotationExample.testException() throws java.lang.Exception
     * java.lang.Exception: error
     * at org.ChapterIV.Async.AsyncAnnotationExample.testException(AsyncAnnotationExample.java:37)
     * at org.ChapterIV.Async.AsyncAnnotationExample$$FastClassBySpringCGLIB$$bf7dd9af.invoke(<generated>)
     * at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
     * at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:749)
     * at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
     * at org.springframework.aop.interceptor.AsyncExecutionInterceptor.lambda$invoke$0(AsyncExecutionInterceptor.java:115)
     * at java.util.concurrent.FutureTask.run(FutureTask.java:266)
     * at java.lang.Thread.run(Thread.java:748)
     */
    public static void main(String[] arg) throws Exception {
        // 1.创建容器上下文
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                new String[]{"beans-annotation.xml"});

        // 2. 获取AsyncExecutorExample实例并调用打印方法
        System.out.println(Thread.currentThread().getName() + " begin ");
        AsyncAnnotationExample asyncCommentExample = applicationContext.getBean(AsyncAnnotationExample.class);
        asyncCommentExample.testException();
        System.out.println(Thread.currentThread().getName() + " end ");
    }
}
