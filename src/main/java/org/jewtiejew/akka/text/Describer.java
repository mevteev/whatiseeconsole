package org.jewtiejew.akka.text;

import com.amazonaws.services.rekognition.model.AgeRange;
import com.amazonaws.services.rekognition.model.Emotion;
import com.amazonaws.services.rekognition.model.FaceDetail;

import java.util.List;

/**
 * Created by mike on 17.08.18.
 */
public class Describer {

    public static float THRESHOLD = 80F;

    public String describeFace(FaceDetail face) {

        StringBuilder text = new StringBuilder();

        text.append("I see ");
        boolean isMan = face.getGender().getValue().equals("Male");
        text.append(isMan? "man" : "woman");
        text.append("\n");

        //age
        AgeRange ageRange = face.getAgeRange();
        text.append(sex(isMan));
        text.append("is estimated to be between ");
        text.append(ageRange.getLow().toString());
        text.append(" and ");
        text.append(ageRange.getHigh().toString());
        text.append(" years old.\n");

        // smile
        if (Boolean.TRUE.equals(face.getSmile().getValue())) {
            text.append(sex(isMan));
            text.append("is smiling.\n");
        }

        // eyeglasses / sunglasses
        if (Boolean.TRUE.equals(face.getEyeglasses().getValue())) {
            text.append(sex(isMan));
            if (Boolean.TRUE.equals(face.getSunglasses().getValue())) {
                text.append("is wearing sunglasses.\n");
            } else {
                text.append("is wearing eyeglasses.\n");
            }
        }

        // beard
        if (Boolean.TRUE.equals(face.getBeard().getValue())) {
            text.append(sex(isMan));
            text.append("have beard.\n");
        }

        // mustache
        if (Boolean.TRUE.equals(face.getMustache().getValue())) {
            text.append(sex(isMan));
            text.append("have mustache.\n");
        }

        // emotions
        List<Emotion> emotions = face.getEmotions();

        for (Emotion emotion : emotions) {
            if (emotion.getConfidence() > THRESHOLD) {
                text.append(sex(isMan));
                text.append(" is ");
                text.append(emotion.getType().toLowerCase());
                text.append("\n");
            }
        }

        return text.toString();

    }

    private String sex(boolean isMan) {
        return isMan ? "He " : "She ";
    }
}
