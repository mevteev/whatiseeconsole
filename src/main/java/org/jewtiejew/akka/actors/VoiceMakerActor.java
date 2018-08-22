package org.jewtiejew.akka.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import org.jewtiejew.akka.messages.Sentence;
import org.jewtiejew.akka.messages.VoiceResult;

/**
 * Created by mike on 15.08.18.
 */
public class VoiceMakerActor extends AbstractActor {

    private ActorRef speaker;

    static public Props props(ActorRef speaker) {
        return Props.create(VoiceMakerActor.class, () -> new VoiceMakerActor(speaker));
    }

    public VoiceMakerActor(ActorRef speaker) {
        this.speaker = speaker;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Sentence.class, sentence -> {
                    speaker.tell(new VoiceResult(makeVoice(sentence.sentence)), getSelf());
                })
                .build();
    }

    private String makeVoice(String sentence) {
        return sentence;
    }
}
