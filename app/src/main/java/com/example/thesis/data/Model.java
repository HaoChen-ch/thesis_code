package com.example.thesis.data;

import android.content.Context;
import android.content.res.AssetManager;

import org.dmg.pmml.Field;
import org.dmg.pmml.FieldName;
import org.dmg.pmml.PMML;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jpmml.evaluator.Evaluator;
import org.jpmml.evaluator.ModelEvaluator;
import org.jpmml.evaluator.ModelEvaluatorFactory;
import org.jpmml.evaluator.ProbabilityDistribution;
import org.jpmml.model.SerializationUtil;

public class Model {
    private Evaluator evaluator;

    public Model(Context context) {
        AssetManager assetManager = context.getAssets();
        try {
            try (InputStream is = assetManager.open("model.pmml.ser")) {
                evaluator = createEvaluator(is);
                System.out.println("Input (aka feature) fields: +++++++++++++" + evaluator.getInputFields());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//
//
    }

    private Evaluator createEvaluator(InputStream is) throws Exception {
        PMML pmml = SerializationUtil.deserializePMML(is);

        ModelEvaluatorFactory modelEvaluatorFactory = ModelEvaluatorFactory.newInstance();

        ModelEvaluator<?> modelEvaluator = modelEvaluatorFactory.newModelEvaluator(pmml);
        modelEvaluator.verify();

        return modelEvaluator;
    }


    public String predict(Float[] feature) {
        Map<FieldName, Float> arguments = new HashMap<>();
        List<Integer> fieldNames = Arrays.asList(543, 219, 1013, 1213, 1040, 3, 246, 1364, 554, 1375, 84);
        List<String> transportations = Arrays.asList("静止", "走路", "跑步", "骑自行车", "开汽车", "坐公交车", "坐火车", "坐地铁");
        for (int i = 0; i < fieldNames.size(); i++) {
            arguments.put(FieldName.create(String.valueOf(fieldNames.get(i))), feature[i]);
        }

        Map<FieldName, ?> results = evaluator.evaluate(arguments);

        System.out.println(results.toString());
        ProbabilityDistribution re = (ProbabilityDistribution) results.get(FieldName.create("_target"));
        System.out.println("result+++++" + re.getResult());
        return transportations.get(Integer.parseInt(re.getResult().toString()));

    }
}
