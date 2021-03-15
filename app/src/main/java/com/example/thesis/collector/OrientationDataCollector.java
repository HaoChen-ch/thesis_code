package com.example.thesis.collector;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;
import java.util.List;

public class OrientationDataCollector implements SensorEventListener {
    private List<float[]> data = new ArrayList<>();

    public OrientationDataCollector(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            final float[] quaternionValue = new float[4];
            SensorManager.getQuaternionFromVector(quaternionValue, event.values);
            data.add(quaternionValue);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void clear() {
        data.clear();
    }

    public List<float[]> getData() {
        return data;
    }

    public boolean isSuccess() {
        return data.size() == 500;
    }
}
