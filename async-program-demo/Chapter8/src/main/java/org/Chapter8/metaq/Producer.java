package org.Chapter8.metaq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class Producer {

    /**
     * SendResult [sendStatus=SEND_OK, msgId=C0A8322D31DD18B4AAC237F37E4A0000, offsetMsgId=C0A8322D00002A9F000000000000191E, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=2], queueOffset=9]
     * SendResult [sendStatus=SEND_OK, msgId=C0A8322D31DD18B4AAC237F37E530001, offsetMsgId=C0A8322D00002A9F00000000000019DC, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=3], queueOffset=7]
     * SendResult [sendStatus=SEND_OK, msgId=C0A8322D31DD18B4AAC237F37E580002, offsetMsgId=C0A8322D00002A9F0000000000001A9A, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=0], queueOffset=8]
     * SendResult [sendStatus=SEND_OK, msgId=C0A8322D31DD18B4AAC237F37E5C0003, offsetMsgId=C0A8322D00002A9F0000000000001B58, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=1], queueOffset=9]
     * SendResult [sendStatus=SEND_OK, msgId=C0A8322D31DD18B4AAC237F37E5E0004, offsetMsgId=C0A8322D00002A9F0000000000001C16, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=2], queueOffset=10]
     * SendResult [sendStatus=SEND_OK, msgId=C0A8322D31DD18B4AAC237F37E600005, offsetMsgId=C0A8322D00002A9F0000000000001CD4, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=3], queueOffset=8]
     * SendResult [sendStatus=SEND_OK, msgId=C0A8322D31DD18B4AAC237F37E640006, offsetMsgId=C0A8322D00002A9F0000000000001D92, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=0], queueOffset=9]
     * SendResult [sendStatus=SEND_OK, msgId=C0A8322D31DD18B4AAC237F37E660007, offsetMsgId=C0A8322D00002A9F0000000000001E50, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=1], queueOffset=10]
     * SendResult [sendStatus=SEND_OK, msgId=C0A8322D31DD18B4AAC237F37E680008, offsetMsgId=C0A8322D00002A9F0000000000001F0E, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=2], queueOffset=11]
     * SendResult [sendStatus=SEND_OK, msgId=C0A8322D31DD18B4AAC237F37E6B0009, offsetMsgId=C0A8322D00002A9F0000000000001FCC, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=3], queueOffset=9]
     */
    public static void main(String[] args) throws MQClientException, InterruptedException {
        // 1. 创建生产者实例
        DefaultMQProducer producer = new DefaultMQProducer("jiaduo-producer-group");
        // 2. 设置nameserver地址，多个可以使用;分隔
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.setSendMsgTimeout(1000);
        // 3. 启动生产者
        producer.start();

        // 4. 发送消息
        for (int i = 0; i < 10; i++) {
            try {

                // 4.1 创建消息体,topic为TopicTest，tag为TagA
                Message msg = new Message("TopicTest" /* Topic */, "TagA" /* Tag */,
                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                );

                // 4.2 发送消息
                SendResult sendResult = producer.send(msg);
                // 4.3
				System.out.printf("%s%n", sendResult);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 5. 关闭
		producer.shutdown();
	}

}
