package org.jewtiejew.akka.messages;

/**
 * Created by mike on 15.08.18.
 */
public class FileNameMsg {
    public final String filePath;

    public FileNameMsg(String filePath) {
        this.filePath = filePath;
    }
}
