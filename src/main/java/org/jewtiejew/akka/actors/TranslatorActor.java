package org.jewtiejew.akka.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.jewtiejew.akka.amazon.Translator;
import org.jewtiejew.akka.messages.Sentence;

/**
 * Created by mike on 15.08.18.
 */
public class TranslatorActor extends AbstractActor {

    private ActorRef voiceMaker;

    private Translator translator = new Translator();

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);


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
        String result = translator.translate(sentence, "en", "ru");
        log.info(result);

        return result;
    }
}
