package org.jewtiejew.akka.messages;

/**
 * Created by mike on 15.08.18.
 */
public class ImageAttributes {

    public String[] attributes;

    public ImageAttributes(String[] attributes) {
        this.attributes = attributes; // todo: not thread safe
    }
}
