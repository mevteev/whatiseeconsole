package org.jewtiejew.akka.service;

import java.io.Closeable;

/**
 * Created by mike on 16.08.18.
 */
public interface WhatISeeService extends Closeable {

    void describePictureToLog(String path);

    String describePicture(String path);


}
