package com.example.thesis.ui.dashboard;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class DashboardViewModel extends ViewModel implements SensorEventListener {

    private MutableLiveData<String> mText= new MutableLiveData<>();
    private SensorManager mSensor = null;
    public DashboardViewModel() {
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setContext(Context context) {
        mSensor = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensor.registerListener(this, mSensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 5000);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        System.out.println(sensorEvent.sensor.getName());
        mText.setValue(String.valueOf(sensorEvent.values[0]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}