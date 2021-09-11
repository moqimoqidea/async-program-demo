package org.Chapter8.metaq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class ProducerAsync {

    /**
     * onSuccess:SendResult [sendStatus=SEND_OK, msgId=C0A8322D316918B4AAC237F270B60000, offsetMsgId=C0A8322D00002A9F000000000000076C, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=0], queueOffset=2]
     * onSuccess:SendResult [sendStatus=SEND_OK, msgId=C0A8322D316918B4AAC237F271CF0001, offsetMsgId=C0A8322D00002A9F0000000000000831, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=1], queueOffset=2]
     * onSuccess:SendResult [sendStatus=SEND_OK, msgId=C0A8322D316918B4AAC237F273C60002, offsetMsgId=C0A8322D00002A9F00000000000008F6, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=0], queueOffset=3]
     * onSuccess:SendResult [sendStatus=SEND_OK, msgId=C0A8322D316918B4AAC237F275BC0003, offsetMsgId=C0A8322D00002A9F00000000000009BB, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=1], queueOffset=3]
     * onSuccess:SendResult [sendStatus=SEND_OK, msgId=C0A8322D316918B4AAC237F277B00004, offsetMsgId=C0A8322D00002A9F0000000000000A80, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=0], queueOffset=4]
     * onSuccess:SendResult [sendStatus=SEND_OK, msgId=C0A8322D316918B4AAC237F279A50005, offsetMsgId=C0A8322D00002A9F0000000000000B45, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=3], queueOffset=3]
     * onSuccess:SendResult [sendStatus=SEND_OK, msgId=C0A8322D316918B4AAC237F27B990006, offsetMsgId=C0A8322D00002A9F0000000000000C0A, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=1], queueOffset=4]
     * onSuccess:SendResult [sendStatus=SEND_OK, msgId=C0A8322D316918B4AAC237F27D8E0007, offsetMsgId=C0A8322D00002A9F0000000000000CCF, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=0], queueOffset=5]
     * onSuccess:SendResult [sendStatus=SEND_OK, msgId=C0A8322D316918B4AAC237F27F880008, offsetMsgId=C0A8322D00002A9F0000000000000D94, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=1], queueOffset=5]
     * onSuccess:SendResult [sendStatus=SEND_OK, msgId=C0A8322D316918B4AAC237F281850009, offsetMsgId=C0A8322D00002A9F0000000000000E59, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=2], queueOffset=3]
     * onSuccess:SendResult [sendStatus=SEND_OK, msgId=C0A8322D316918B4AAC237F28378000A, offsetMsgId=C0A8322D00002A9F0000000000000F1E, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=1], queueOffset=6]
     * onSuccess:SendResult [sendStatus=SEND_OK, msgId=C0A8322D316918B4AAC237F2856E000B, offsetMsgId=C0A8322D00002A9F0000000000000FE5, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=2], queueOffset=4]
     * onSuccess:SendResult [sendStatus=SEND_OK, msgId=C0A8322D316918B4AAC237F28766000C, offsetMsgId=C0A8322D00002A9F00000000000010AC, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=1], queueOffset=7]
     * onSuccess:SendResult [sendStatus=SEND_OK, msgId=C0A8322D316918B4AAC237F2895C000D, offsetMsgId=C0A8322D00002A9F0000000000001173, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=0], queueOffset=6]
     * onSuccess:SendResult [sendStatus=SEND_OK, msgId=C0A8322D316918B4AAC237F28B50000E, offsetMsgId=C0A8322D00002A9F000000000000123A, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=2], queueOffset=5]
     * onSuccess:SendResult [sendStatus=SEND_OK, msgId=C0A8322D316918B4AAC237F28D47000F, offsetMsgId=C0A8322D00002A9F0000000000001301, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=1], queueOffset=8]
     * onSuccess:SendResult [sendStatus=SEND_OK, msgId=C0A8322D316918B4AAC237F28F3E0010, offsetMsgId=C0A8322D00002A9F00000000000013C8, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=2], queueOffset=6]
     * onSuccess:SendResult [sendStatus=SEND_OK, msgId=C0A8322D316918B4AAC237F291340011, offsetMsgId=C0A8322D00002A9F000000000000148F, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=3], queueOffset=4]
     * onSuccess:SendResult [sendStatus=SEND_OK, msgId=C0A8322D316918B4AAC237F293290012, offsetMsgId=C0A8322D00002A9F0000000000001556, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=2], queueOffset=7]
     * onSuccess:SendResult [sendStatus=SEND_OK, msgId=C0A8322D316918B4AAC237F295220013, offsetMsgId=C0A8322D00002A9F000000000000161D, messageQueue=MessageQueue [topic=TopicTest, brokerName=moqi-13mbp, queueId=3], queueOffset=5]
     */
    public static void main(String[] args) throws MQClientException, InterruptedException {

        // 1. 创建生产者实例
        DefaultMQProducer producer = new DefaultMQProducer("jiaduo-producer-group");
        // 2. 设置nameserver地址，多个可以使用;分隔
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.setSendMsgTimeout(1000);
        producer.setRetryTimesWhenSendAsyncFailed(0);
        // 3. 启动生产者
        producer.start();

        // 4. 发送消息
		for (int i = 0; i < 20; i++) {
			try {

				// 4.1 创建消息体,topic为TopicTest，tag为TagA
				Message msg = new Message("TopicTest" /* Topic */, "TagA" /* Tag */, i + "",
						("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
				);

				// 4.2 异步发送消息
				producer.send(msg, new SendCallback() {

					@Override
					public void onSuccess(SendResult sendResult) {
						System.out.printf("onSuccess:%s%n", sendResult);

					}

					@Override
					public void onException(Throwable e) {
						System.out.printf("onException:%s%n", e);

					}
				});
				// 4.3
				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 5. 关闭
		Thread.sleep(100000);
		producer.shutdown();
	}
}
