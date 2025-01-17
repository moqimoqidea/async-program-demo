package org.Chapter8.metaq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class ProducerSync {

    /**
     * SendResult [sendStatus=SEND_OK, msgId=C0A8322D317818B4AAC237F29B5F0000, offsetMsgId=C0A8322D00002A9F00000000000016E4, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=2], queueOffset=8]
     * SendResult [sendStatus=SEND_OK, msgId=C0A8322D317818B4AAC237F29B6A0001, offsetMsgId=C0A8322D00002A9F00000000000017A2, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=3], queueOffset=6]
     * SendResult [sendStatus=SEND_OK, msgId=C0A8322D317818B4AAC237F29B6C0002, offsetMsgId=C0A8322D00002A9F0000000000001860, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=0], queueOffset=7]
     */
	public static void main(String[] args) throws MQClientException, InterruptedException {

		// 1. 创建生产者实例
		DefaultMQProducer producer = new DefaultMQProducer("my-producer-group");
		// 2. 设置nameserver地址，多个可以使用;分隔
		producer.setNamesrvAddr("127.0.0.1:9876");
		// 3. 启动生产者
		producer.start();

		// 4. 发送消息
		for (int i = 0; i < 3; i++) {
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
