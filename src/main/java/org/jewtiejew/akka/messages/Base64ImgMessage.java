package org.jewtiejew.akka.messages;

import java.nio.ByteBuffer;

/**
 * Created by mike on 15.08.18.
 */
public class Base64ImgMessage {

    public ByteBuffer img;

    public Base64ImgMessage(ByteBuffer img) {
        this.img = img;
    }
}
