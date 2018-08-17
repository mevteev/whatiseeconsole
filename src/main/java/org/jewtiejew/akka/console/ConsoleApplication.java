package org.jewtiejew.akka.console;

import org.jewtiejew.akka.service.WhatISeeService;
import org.jewtiejew.akka.service.WhatISeeSimpleService;

import java.io.IOException;

/**
 * Created by mike on 16.08.18.
 */
public class ConsoleApplication {

    public static void main(String[] args) {

        try (WhatISeeService service = new WhatISeeSimpleService()) {
            //service.describePicture("/home/mike/Изображения/IMG_0526.JPG");
            service.describePicture("/home/mike/Изображения/PPF_9204_small.JPG");
            //service.describePicture("/home/mike/Изображения/IMG_0404.JPG");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
