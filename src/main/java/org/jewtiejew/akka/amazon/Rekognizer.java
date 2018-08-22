package org.jewtiejew.akka.amazon;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by mike on 17.08.18.
 */
public class Rekognizer {

    private AmazonRekognition rekognitionClient;

    public Rekognizer() {
        rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();
    }

    public List<Label> getLabels(ByteBuffer image) {

        long startTime = System.currentTimeMillis();

        List<Label> labels = new ArrayList<>();

        DetectLabelsRequest request = new DetectLabelsRequest()
                .withImage(new Image()
                    .withBytes(image))
                .withMaxLabels(5)
                .withMinConfidence(77F);

        try {
            DetectLabelsResult result = rekognitionClient.detectLabels(request);
            labels = result.getLabels();
        } catch (AmazonRekognitionException e) {
            e.printStackTrace();
        }

        System.out.println("Detect labels: " + (System.currentTimeMillis() - startTime) / 1000 );


        return labels;
    }

    public List<FaceDetail> getFaces(ByteBuffer image) {

        long startTime = System.currentTimeMillis();

        List<FaceDetail> faceDetails = new ArrayList<>();

        DetectFacesRequest request = new DetectFacesRequest()
                .withImage(new Image()
                    .withBytes(image))
                .withAttributes(Attribute.ALL);

        try {
            DetectFacesResult result = rekognitionClient.detectFaces(request);
            faceDetails = result.getFaceDetails();

        } catch(AmazonRekognitionException e) {
            e.printStackTrace();
        }

        System.out.println("Detect face: " + (System.currentTimeMillis() - startTime) / 1000 );
        return faceDetails;
    }

    public List<Celebrity> getCelebrities(ByteBuffer image) {
        List<Celebrity> celebs;

        RecognizeCelebritiesRequest request = new RecognizeCelebritiesRequest()
                .withImage(new Image()
                    .withBytes(image));

        RecognizeCelebritiesResult result = rekognitionClient.recognizeCelebrities(request);

        celebs = result.getCelebrityFaces();

        return celebs;
    }
}
