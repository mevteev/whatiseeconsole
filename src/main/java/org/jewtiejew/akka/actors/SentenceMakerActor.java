package org.jewtiejew.akka.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.amazonaws.services.rekognition.model.FaceDetail;
import org.jewtiejew.akka.messages.ImageAttributes;
import org.jewtiejew.akka.messages.Sentence;
import org.jewtiejew.akka.text.Describer;

/**
 * Created by mike on 15.08.18.
 */
public class SentenceMakerActor extends AbstractActor {

    private ActorRef translator;

    private Describer describer = new Describer();

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);


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
                    translator.tell(new Sentence(makeSentense(attrs)), getSelf());
                })
                .build();
    }

    private String makeSentense(ImageAttributes attributes) {
        StringBuilder text = new StringBuilder();
        text.append(describer.describeLabels(attributes.getLabels()));

        for (FaceDetail face : attributes.getFaces()) {
            text.append(describer.describeFace(face));
        }

        text.append(describer.describeCelebs(attributes.getCelebrities()));
        log.info(text.toString());
        return text.toString();
    }
}
