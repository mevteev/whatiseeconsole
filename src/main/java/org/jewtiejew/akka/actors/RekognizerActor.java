package org.jewtiejew.akka.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import org.jewtiejew.akka.amazon.Rekognizer;
import org.jewtiejew.akka.messages.Base64ImgMessage;
import org.jewtiejew.akka.messages.ImageAttributes;

/**
 * Created by mike on 15.08.18.
 */
public class RekognizerActor extends AbstractActor {

    private ActorRef sentenseMaker;

    private Rekognizer rekognizer;

    static public Props props(ActorRef rekognizer) {
        return Props.create(RekognizerActor.class, () -> new RekognizerActor(rekognizer));
    }

    public RekognizerActor(ActorRef sentenseMaker) {
        this.sentenseMaker = sentenseMaker;
        this.rekognizer = new Rekognizer();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Base64ImgMessage.class, img -> {
                    sentenseMaker.tell(recognize(img), getSelf());
                })
                .build();
    }

    private ImageAttributes recognize(Base64ImgMessage img) {
        return new ImageAttributes(
                rekognizer.getLabels(img.img),
                rekognizer.getFaces(img.img),
                rekognizer.getCelebrities(img.img));
    }
}
