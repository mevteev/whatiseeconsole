package org.jewtiejew.akka.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import org.jewtiejew.akka.messages.ImageAttributes;
import org.jewtiejew.akka.messages.Sentence;

/**
 * Created by mike on 15.08.18.
 */
public class SentenceMakerActor extends AbstractActor {

    private ActorRef translator;

    static public Props props(ActorRef rekognizer) {
        return Props.create(SentenceMakerActor.class, () -> new SentenceMakerActor(rekognizer));
    }


    public SentenceMakerActor(ActorRef translator) {
        this.translator = translator;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ImageAttributes.class, attrs -> {
                    translator.tell(new Sentence(makeSentense(attrs.attributes)), getSelf());
                })
                .build();
    }

    private String makeSentense(String[] attributes) {
        return "";
    }
}
