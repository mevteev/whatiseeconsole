package org.jewtiejew.akka.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import org.jewtiejew.akka.messages.Sentence;

/**
 * Created by mike on 15.08.18.
 */
public class TranslatorActor extends AbstractActor {

    private ActorRef voiceMaker;

    static public Props props(ActorRef voiceMaker) {
        return Props.create(TranslatorActor.class, () -> new TranslatorActor(voiceMaker));
    }

    public TranslatorActor(ActorRef voiceMaker) {
        this.voiceMaker = voiceMaker;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Sentence.class, sentence -> {
                    voiceMaker.tell(new Sentence(translate(sentence.sentence)), getSelf());
                })
                .build();
    }

    private String translate(String sentence) {
        return "";
    }
}
