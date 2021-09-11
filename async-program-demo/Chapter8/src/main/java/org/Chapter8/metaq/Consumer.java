package org.Chapter8.metaq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;

public class Consumer {

    /**
     * Consumer Started.
     * ConsumeMessageThread_4 Receive New Messages: C0A8322D315018B4AAC237F23B580007 Hello RocketMQ 7
     * ConsumeMessageThread_10 Receive New Messages: C0A8322D316918B4AAC237F275BC0003 Hello RocketMQ 3
     * ConsumeMessageThread_11 Receive New Messages: C0A8322D316918B4AAC237F273C60002 Hello RocketMQ 2
     * ConsumeMessageThread_16 Receive New Messages: C0A8322D316918B4AAC237F27D8E0007 Hello RocketMQ 7
     * ConsumeMessageThread_7 Receive New Messages: C0A8322D316918B4AAC237F270B60000 Hello RocketMQ 0
     * ConsumeMessageThread_11 Receive New Messages: C0A8322D317818B4AAC237F29B5F0000 Hello RocketMQ 0
     * ConsumeMessageThread_18 Receive New Messages: C0A8322D316918B4AAC237F2895C000D Hello RocketMQ 13
     * ConsumeMessageThread_17 Receive New Messages: C0A8322D316918B4AAC237F2856E000B Hello RocketMQ 11
     * ConsumeMessageThread_9 Receive New Messages: C0A8322D315018B4AAC237F23B5B0008 Hello RocketMQ 8
     * ConsumeMessageThread_20 Receive New Messages: C0A8322D316918B4AAC237F28F3E0010 Hello RocketMQ 16
     * ConsumeMessageThread_8 Receive New Messages: C0A8322D316918B4AAC237F271CF0001 Hello RocketMQ 1
     * ConsumeMessageThread_15 Receive New Messages: C0A8322D316918B4AAC237F27F880008 Hello RocketMQ 8
     * ConsumeMessageThread_5 Receive New Messages: C0A8322D315018B4AAC237F23B560006 Hello RocketMQ 6
     * ConsumeMessageThread_13 Receive New Messages: C0A8322D316918B4AAC237F281850009 Hello RocketMQ 9
     * ConsumeMessageThread_14 Receive New Messages: C0A8322D316918B4AAC237F277B00004 Hello RocketMQ 4
     * ConsumeMessageThread_1 Receive New Messages: C0A8322D315018B4AAC237F23B480003 Hello RocketMQ 3
     * ConsumeMessageThread_12 Receive New Messages: C0A8322D316918B4AAC237F27B990006 Hello RocketMQ 6
     * ConsumeMessageThread_3 Receive New Messages: C0A8322D315018B4AAC237F23B1D0000 Hello RocketMQ 0
     * ConsumeMessageThread_19 Receive New Messages: C0A8322D316918B4AAC237F28B50000E Hello RocketMQ 14
     * ConsumeMessageThread_2 Receive New Messages: C0A8322D315018B4AAC237F23B450002 Hello RocketMQ 2
     * ConsumeMessageThread_6 Receive New Messages: C0A8322D315018B4AAC237F23B4A0004 Hello RocketMQ 4
     * ConsumeMessageThread_11 Receive New Messages: C0A8322D316918B4AAC237F28D47000F Hello RocketMQ 15
     * ConsumeMessageThread_7 Receive New Messages: C0A8322D316918B4AAC237F28766000C Hello RocketMQ 12
     * ConsumeMessageThread_10 Receive New Messages: C0A8322D316918B4AAC237F28378000A Hello RocketMQ 10
     * ConsumeMessageThread_4 Receive New Messages: C0A8322D317818B4AAC237F29B6C0002 Hello RocketMQ 2
     * ConsumeMessageThread_16 Receive New Messages: C0A8322D316918B4AAC237F293290012 Hello RocketMQ 18
     * ConsumeMessageThread_17 Receive New Messages: C0A8322D315018B4AAC237F23B4E0005 Hello RocketMQ 5
     * ConsumeMessageThread_5 Receive New Messages: C0A8322D317818B4AAC237F29B6A0001 Hello RocketMQ 1
     * ConsumeMessageThread_15 Receive New Messages: C0A8322D316918B4AAC237F295220013 Hello RocketMQ 19
     * ConsumeMessageThread_8 Receive New Messages: C0A8322D316918B4AAC237F291340011 Hello RocketMQ 17
     * ConsumeMessageThread_20 Receive New Messages: C0A8322D316918B4AAC237F279A50005 Hello RocketMQ 5
     * ConsumeMessageThread_9 Receive New Messages: C0A8322D315018B4AAC237F23B600009 Hello RocketMQ 9
     * ConsumeMessageThread_18 Receive New Messages: C0A8322D315018B4AAC237F23B3F0001 Hello RocketMQ 1
     * ConsumeMessageThread_13 Receive New Messages: C0A8322D31DD18B4AAC237F37E4A0000 Hello RocketMQ 0
     * ConsumeMessageThread_14 Receive New Messages: C0A8322D31DD18B4AAC237F37E530001 Hello RocketMQ 1
     * ConsumeMessageThread_1 Receive New Messages: C0A8322D31DD18B4AAC237F37E580002 Hello RocketMQ 2
     * ConsumeMessageThread_12 Receive New Messages: C0A8322D31DD18B4AAC237F37E5C0003 Hello RocketMQ 3
     * ConsumeMessageThread_3 Receive New Messages: C0A8322D31DD18B4AAC237F37E5E0004 Hello RocketMQ 4
     * ConsumeMessageThread_19 Receive New Messages: C0A8322D31DD18B4AAC237F37E600005 Hello RocketMQ 5
     * ConsumeMessageThread_2 Receive New Messages: C0A8322D31DD18B4AAC237F37E640006 Hello RocketMQ 6
     * ConsumeMessageThread_6 Receive New Messages: C0A8322D31DD18B4AAC237F37E660007 Hello RocketMQ 7
     * ConsumeMessageThread_11 Receive New Messages: C0A8322D31DD18B4AAC237F37E680008 Hello RocketMQ 8
     * ConsumeMessageThread_7 Receive New Messages: C0A8322D31DD18B4AAC237F37E6B0009 Hello RocketMQ 9
     */
    public static void main(String[] args) throws InterruptedException, MQClientException {

        // 1. 创建消费实例 和 配置ns地址
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("my-consumer-group");
        consumer.setNamesrvAddr("127.0.0.1:9876");

        // 2. 消费属性配置
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        // 3. 订阅TopicTest topic下所有tag
        consumer.subscribe("TopicTest", "*");
        // 4. 注册回调
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            for (MessageExt msg : msgs) {
                String body = "";
                try {
                    body = new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                System.out.printf("%s Receive New Messages: %s %s %n", Thread.currentThread().getName(),
                        msg.getMsgId(), body);

            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        // 5.启动消费实例
        consumer.start();

        System.out.printf("Consumer Started.%n");
    }
}
