package com.example.thesis.data;
import org.jpmml.evaluator.Evaluator;
import org.jpmml.evaluator.ServiceLoadingModelEvaluatorBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Model {
    public static void main(String[] args) throws IOException {

        File pmmlJarFile = new File("LightGBMAudit.pmml.jar");

        URL pmmlJarURL = (pmmlJarFile.toURI()).toURL();

        Evaluator evaluator = new ServiceLoadingModelEvaluatorBuilder()
                .loadService(pmmlJarURL)
                .build();
    }
}
