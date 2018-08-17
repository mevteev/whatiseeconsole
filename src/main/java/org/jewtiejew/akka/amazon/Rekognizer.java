package org.jewtiejew.akka.amazon;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jewtiejew.akka.text.Describer;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by mike on 17.08.18.
 */
public class Rekognizer {

    private AmazonRekognition rekognitionClient;

    private Describer describer = new Describer(); //todo: just for test

    public Rekognizer() {
        rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();
    }

    public List<Label> getLabels(ByteBuffer image) {
        List<Label> labels = new ArrayList<>();

        DetectLabelsRequest request = new DetectLabelsRequest()
                .withImage(new Image()
                    .withBytes(image))
                .withMaxLabels(10)
                .withMinConfidence(77F);

        try {
            DetectLabelsResult result = rekognitionClient.detectLabels(request);
            labels = result.getLabels();

            System.out.println("Detected labels");
            for (Label label : labels) {
                System.out.println(label.getName() + ": " + label.getConfidence().toString());
            }
        } catch (AmazonRekognitionException e) {
            e.printStackTrace();
        }

        return labels;
    }

    public List<FaceDetail> getFaces(ByteBuffer image) {

        List<FaceDetail> faceDetails = new ArrayList<>();

        DetectFacesRequest request = new DetectFacesRequest()
                .withImage(new Image()
                    .withBytes(image))
                .withAttributes(Attribute.ALL);

        try {
            DetectFacesResult result = rekognitionClient.detectFaces(request);
            faceDetails = result.getFaceDetails();

            for (FaceDetail face : faceDetails) {
                System.out.println(describer.describeFace(face));
                /*
                if (request.getAttributes().contains("ALL")) {
                    AgeRange ageRange = face.getAgeRange();
                    System.out.println("The detected face is estimated to be between "
                            + ageRange.getLow().toString() + " and " + ageRange.getHigh().toString()
                            + " years old.");
                    System.out.println("Here's the complete set of attributes:");
                } else {
                    System.out.println("Here's the default set of attributes:");
                }

                ObjectMapper objectMapper = new ObjectMapper();
                System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(face));
                */
            }

        } catch(AmazonRekognitionException e) {
            e.printStackTrace();
        }
        return faceDetails;
    }

    public List<Celebrity> getCelebrities(ByteBuffer image) {
        List<Celebrity> celebs = new ArrayList<>();

        RecognizeCelebritiesRequest request = new RecognizeCelebritiesRequest()
                .withImage(new Image()
                    .withBytes(image));

        RecognizeCelebritiesResult result = rekognitionClient.recognizeCelebrities(request);

        celebs = result.getCelebrityFaces();
        System.out.println(celebs.size() + " celebrity(s) were recognized\n");

        for (Celebrity celebrity : celebs) {
            System.out.println("Celebrity recognized: " + celebrity.getName());
        }

        return celebs;
    }
}
