package org.jewtiejew.akka.service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import org.jewtiejew.akka.actors.*;
import org.jewtiejew.akka.messages.FileNameMsg;


/**
 * Created by mike on 16.08.18.
 */
public class WhatISeeSimpleService implements WhatISeeService {

    ActorRef speakerActor;
    ActorRef voiceMakerActor;
    ActorRef translatorActor;
    ActorRef sentenceMakerActor;
    ActorRef rekognizerActor;
    ActorRef takePhotoActor;

    ActorSystem system;

    public WhatISeeSimpleService() {
        init();
    }

    @Override
    public void describePictureToLog(String path) {

    }

    @Override
    public String describePicture(String path) {
        takePhotoActor.tell(new FileNameMsg(path), ActorRef.noSender());
        return "";
    }

    public void init() {

        system = ActorSystem.create("WhatISee");

        speakerActor = system.actorOf(SpeakerActor.props(), "speakerActor");
        voiceMakerActor = system.actorOf(VoiceMakerActor.props(speakerActor), "voiceMakerActor");
        translatorActor = system.actorOf(TranslatorActor.props(voiceMakerActor), "translatorActor");
        sentenceMakerActor = system.actorOf(SentenceMakerActor.props(translatorActor), "sentenceMakerActor");
        rekognizerActor = system.actorOf(RekognizerActor.props(sentenceMakerActor), "rekognizerActor");
        takePhotoActor = system.actorOf(TakePhotoActor.props(rekognizerActor), "takePhotoActor");
    }

    @Override
    public void close() {
        system.terminate();
    }
}
