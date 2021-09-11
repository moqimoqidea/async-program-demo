package org.Chapter8.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

public class Client {

    /**
     * over
     * ActorSelection[Anchor(akka://AkkaRemoteClient/deadLetters), Path(/user/CalculatorActor)]
     * [INFO] [09/11/2021 20:02:31.714] [AkkaRemoteClient-akka.actor.default-dispatcher-2] [akka://AkkaRemoteClient/user/$a] Got a calc job, local calculator
     * [INFO] [09/11/2021 20:02:31.717] [AkkaRemoteClient-akka.actor.default-dispatcher-3] [akka://AkkaRemoteClient/deadLetters] Message [org.Chapter8.akka.Messages$Sum] from Actor[akka://AkkaRemoteClient/user/$a#180959994] to Actor[akka://AkkaRemoteClient/deadLetters] was not delivered. [1] dead letters encountered. This logging can be turned off or adjusted with configuration settings 'akka.log-dead-letters' and 'akka.log-dead-letters-during-shutdown'.
     */
	public static void main(String[] args) {
		// 1. 创建Actor系统，会加载application.conf文件
		ActorSystem system = ActorSystem.create("AkkaRemoteClient", ConfigFactory.load());

		// 2. 创建Actor
		ActorRef client = system.actorOf(Props.create(ClientActor.class));

		// 3. 发送消息
		client.tell("DoCalcs", ActorRef.noSender());
		System.out.println("over");
	}

}
