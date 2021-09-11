package org.Chapter8.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

public class LongEventMain {

    /**
     * Thread-2Replication Event: 0
     * Thread-1Persist Event: 0
     * Thread-3Application Event: 0
     * Thread-2Replication Event: 1
     * Thread-1Persist Event: 1
     * Thread-3Application Event: 1
     * Thread-2Replication Event: 2
     * Thread-1Persist Event: 2
     * Thread-3Application Event: 2
     * Thread-2Replication Event: 3
     * Thread-1Persist Event: 3
     * Thread-3Application Event: 3
     * Thread-2Replication Event: 4
     * Thread-1Persist Event: 4
     * Thread-3Application Event: 4
     * Thread-2Replication Event: 5
     * Thread-1Persist Event: 5
     * Thread-3Application Event: 5
     * Thread-2Replication Event: 6
     * Thread-1Persist Event: 6
     * Thread-3Application Event: 6
     * Thread-2Replication Event: 7
     * Thread-1Persist Event: 7
     * Thread-3Application Event: 7
     * Thread-2Replication Event: 8
     * Thread-1Persist Event: 8
     * Thread-3Application Event: 8
     * Thread-2Replication Event: 9
     * Thread-1Persist Event: 9
     * Thread-3Application Event: 9
     * Thread-2Replication Event: 10
     * Thread-1Persist Event: 10
     * Thread-3Application Event: 10
     * Thread-2Replication Event: 11
     * Thread-1Persist Event: 11
     * Thread-3Application Event: 11
     */
    public static void main(String[] args) throws Exception {
        // 1.创建Ring Buffer中事件元素的工厂对象
        LongEventFactory factory = new LongEventFactory();

        // 2.指定Ring Buffer的大小,必须为2的幂次方.
        int bufferSize = 1024;

        // 3.构造Disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<>(factory,
                bufferSize, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());

        // 4.注册消费者
        disruptor.handleEventsWith(new JournalConsumer(), new ReplicationConsumer()).then(new ApplicationConsumer());

        // 5.启动Disruptor, 启动线程运行
        disruptor.start();

        // 6.从Disruptor中获取Ring Buffer。
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        // 7. 创建生产者
        LongEventProducer producer = new LongEventProducer(ringBuffer);

        // 8. 生产元素，并放入RingBuffer
        for (long l = 0; l < 100; l++) {
            producer.onData(l);

            Thread.sleep(1000);
        }

        // 9.挂起当前线程
        Thread.currentThread().join();

    }
}
