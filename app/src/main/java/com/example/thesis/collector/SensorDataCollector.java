package com.example.thesis.collector;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class SensorDataCollector implements SensorEventListener {
    private List<float[]> data = new ArrayList<>();
    private int sensorType;

    public SensorDataCollector(Context context, int sensorType) {
        this.sensorType = sensorType;
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(sensorType), 15000);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == sensorType) {
            data.add(sensorEvent.values);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

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
