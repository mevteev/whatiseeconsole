package org.jewtiejew.akka.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.amazonaws.util.IOUtils;
import org.jewtiejew.akka.messages.Base64ImgMessage;
import org.jewtiejew.akka.messages.FileNameMsg;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Created by mike on 15.08.18.
 */
public class TakePhotoActor extends AbstractActor {

    private ActorRef rekognizer;

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);


    static public Props props(ActorRef rekognizer) {
        return Props.create(TakePhotoActor.class, () -> new TakePhotoActor(rekognizer));
    }

    public TakePhotoActor(ActorRef rekognizer) {
        this.rekognizer = rekognizer;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(FileNameMsg.class, img -> {
                    rekognizer.tell(new Base64ImgMessage(readImage(img.filePath)), getSelf());
                })
                .build();
    }

    private ByteBuffer readImage(String path) {
        ByteBuffer imageBytes = null;

        log.info(path);

        try(InputStream inputStream = new FileInputStream(new File(path))) {
            imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        } catch (IOException e) {
            // // TODO: 17.08.18
            e.printStackTrace();
        }

        return imageBytes;
    }
}
