package com.example.thesis.data;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

public class SensorData implements SensorEventListener {
    private SensorManager mSensor = null;
    private TextView textView = null;

    public SensorData(Context context, TextView textView) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 5000);
        this.textView = textView;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        textView.setText((int) sensorEvent.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
