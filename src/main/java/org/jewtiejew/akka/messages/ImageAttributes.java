package org.jewtiejew.akka.messages;

import com.amazonaws.services.rekognition.model.Celebrity;
import com.amazonaws.services.rekognition.model.FaceDetail;
import com.amazonaws.services.rekognition.model.Label;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mike on 15.08.18.
 */
public class ImageAttributes {

    public String[] attributes;

    private List<Label> labels;
    private List<FaceDetail> faces;
    private List<Celebrity> celebrities;

    public ImageAttributes(String[] attributes) {
        this.attributes = attributes; // todo: not thread safe
    }

    public ImageAttributes(List<Label> labels,
                           List<FaceDetail> faces,
                           List<Celebrity> celebrities) {
        this.labels = new ArrayList<>(labels);
        this.faces = new ArrayList<>(faces);
        this.celebrities = new ArrayList<>(celebrities);
    }

    public List<Label> getLabels() {
        return Collections.unmodifiableList(labels);
    }

    public List<FaceDetail> getFaces() {
        return Collections.unmodifiableList(faces);
    }

    public List<Celebrity> getCelebrities() {
        return Collections.unmodifiableList(celebrities);
    }
}
