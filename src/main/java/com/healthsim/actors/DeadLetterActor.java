package com.healthsim.actors;

import akka.actor.AbstractActor;
import akka.actor.DeadLetter;

public class DeadLetterActor extends AbstractActor {

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(DeadLetter.class, msg -> {
                    System.out.println(msg);
                })
                .build();
    }
}
