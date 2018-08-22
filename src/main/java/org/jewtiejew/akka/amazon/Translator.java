package org.jewtiejew.akka.amazon;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateClient;
import com.amazonaws.services.translate.AmazonTranslateClientBuilder;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;

/**
 * Created by mike on 20.08.18.
 */
public class Translator {

    AWSCredentialsProvider awsCreds;
    AmazonTranslate translate;

    public Translator() {
        awsCreds = DefaultAWSCredentialsProviderChain.getInstance();
        translate = AmazonTranslateClientBuilder.standard()
                .withCredentials(awsCreds)
                .build();

    }

    public String translate(String text, String from, String to) {

        long startTime = System.currentTimeMillis();

        TranslateTextRequest request = new TranslateTextRequest()
                .withText(text)
                .withSourceLanguageCode(from)
                .withTargetLanguageCode(to);
        TranslateTextResult result = translate.translateText(request);

        System.out.println("Translate: " + (System.currentTimeMillis() - startTime) / 1000 );

        return result.getTranslatedText();

    }
}
