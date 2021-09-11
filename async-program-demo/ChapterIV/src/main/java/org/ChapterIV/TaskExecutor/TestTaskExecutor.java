package org.ChapterIV.TaskExecutor;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestTaskExecutor {

    /**
     * Sep 11, 2021 8:53:21 PM org.springframework.context.support.PostProcessorRegistrationDelegate$BeanPostProcessorChecker postProcessAfterInitialization
     * INFO: Bean 'myAsyncUncaughtExceptionHandler' of type [org.ChapterIV.TaskExecutor.MyAsyncUncaughtExceptionHandler] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
     * Sep 11, 2021 8:53:21 PM org.springframework.scheduling.concurrent.ExecutorConfigurationSupport initialize
     * INFO: Initializing ExecutorService 'taskExecutor'
     * main begin
     * main end
     * Sep 11, 2021 8:53:21 PM org.springframework.scheduling.concurrent.ExecutorConfigurationSupport shutdown
     * INFO: Shutting down ExecutorService 'taskExecutor'
     * taskExecutor-2 Message1
     * taskExecutor-3 Message2
     * taskExecutor-1 Message0
     * taskExecutor-4 Message3
     * taskExecutor-5 Message4
     * taskExecutor-2 Message5
     */
    public static void main(String[] arg) {
        // 1.创建容器上下文
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                new String[]{"beans.xml"});

        // 2. 获取AsyncExecutorExample实例并调用打印方法
        System.out.println(Thread.currentThread().getName() + " begin ");
        AsyncExecutorExample asyncExecutorExample = applicationContext.getBean(AsyncExecutorExample.class);
        asyncExecutorExample.printMessages();
        System.out.println(Thread.currentThread().getName() + " end ");

        // 3.关闭执行器，释放线程
		asyncExecutorExample.shutdown();

	}

}
