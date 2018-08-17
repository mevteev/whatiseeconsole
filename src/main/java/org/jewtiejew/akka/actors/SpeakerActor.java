package org.jewtiejew.akka.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.jewtiejew.akka.messages.VoiceResult;

/**
 * Created by mike on 15.08.18.
 */
public class SpeakerActor extends AbstractActor {

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    static public Props props() {
        return Props.create(SpeakerActor.class, () -> new SpeakerActor());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(VoiceResult.class, mp3 -> {
                    log.info(mp3.mp3);
                })
                .build();
    }
}
