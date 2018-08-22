package org.jewtiejew.akka.amazon;

import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.polly.AmazonPollyClientBuilder;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import com.amazonaws.services.polly.model.VoiceId;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by mike on 20.08.18.
 */
public class Speaker {
    
    AmazonPolly client;
    
    public Speaker() {
        client = AmazonPollyClientBuilder.defaultClient();
    }
    
    
    public void speak(String text) {
        
        String outputFileName = "/tmp/speech.mp3";

        SynthesizeSpeechRequest synthesizeSpeechRequest = new SynthesizeSpeechRequest()
                .withOutputFormat(OutputFormat.Mp3)
                .withVoiceId(VoiceId.Maxim)     //// TODO: 20.08.18  
                .withText(text);
        
        try (FileOutputStream outputStream = new FileOutputStream(new File(outputFileName))) {
            SynthesizeSpeechResult synthesizeSpeechResult = client.synthesizeSpeech(synthesizeSpeechRequest);
            byte[] buffer = new byte[2 * 1024];
            int readBytes;
            
            try (InputStream in = synthesizeSpeechResult.getAudioStream()) {
                while ((readBytes = in.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, readBytes);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace(); //// TODO: 20.08.18
        }
    }
}
