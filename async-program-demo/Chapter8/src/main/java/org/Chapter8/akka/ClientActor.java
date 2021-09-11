package org.Chapter8.akka;

import akka.actor.ActorSelection;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ClientActor extends UntypedActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private final ActorSelection actorRef = getContext()
            .actorSelection("akka.tcp://AkkaRemoteServer@127.0.0.1:2552/user/CalculatorActor");

    // private ActorRef actorRef =
    // getContext().actorOf(Props.create(CalculatorActor.class), "calculatorActor");

    @Override
    public void onReceive(Object message) {
        if ("DoCalcs".equals(message)) {

            // log.info("Got a calc job, send it to the remote calculator");
            log.info("Got a calc job, local calculator");

            System.out.println(actorRef);
            actorRef.tell(new Messages.Sum(1, 2), getSelf());

        } else if (message instanceof Messages.Result) {
            Messages.Result result = (Messages.Result) message;
            log.info("Got result back from calculator: {}", result.getResult());
        }
	}

}
