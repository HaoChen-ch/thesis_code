package com.example.thesis.data;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;
import java.util.List;

public class DataCollector implements SensorEventListener {
    private final List<Float> acc_x = new ArrayList<>();
    private final List<Float> acc_y = new ArrayList<>();
    private final List<Float> acc_z = new ArrayList<>();
    private final List<Float> ori_w = new ArrayList<>();
    private final List<Float> ori_x = new ArrayList<>();
    private final List<Float> ori_y = new ArrayList<>();
    private final List<Float> ori_z = new ArrayList<>();
    private final List<Float> gry_x = new ArrayList<>();
    private final List<Float> gry_y = new ArrayList<>();
    private final List<Float> gry_z = new ArrayList<>();
    private final List<Float> lin_x = new ArrayList<>();
    private final List<Float> lin_y = new ArrayList<>();
    private final List<Float> lin_z = new ArrayList<>();
    private final List<Float> gra_x = new ArrayList<>();
    private final List<Float> gra_y = new ArrayList<>();
    private final List<Float> gra_z = new ArrayList<>();
    private final List<Float> magnetic_x = new ArrayList<>();
    private final List<Float> magnetic_y = new ArrayList<>();
    private final List<Float> magnetic_z = new ArrayList<>();

    public DataCollector(Context context) {
        int sp = 10000;
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sp);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), sp);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), sp);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), sp);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR), sp);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), sp);
    }

    public List<Float> getAccX() {
        return acc_x;
    }

    public List<Float> getAccY() {
        return acc_y;
    }

    public List<Float> getAccZ() {
        return acc_z;
    }

    public List<Float> getGryX() {
        return gry_x;
    }

    public List<Float> getGryY() {
        return gry_y;
    }

    public List<Float> getGryZ() {
        return gry_z;
    }

    public List<Float> getLAccX() {
        return lin_x;
    }

    public List<Float> getLAccY() {
        return lin_y;
    }

    public List<Float> getLAccZ() {
        return lin_z;
    }

    public List<Float> getGraX() {
        return gra_x;
    }

    public List<Float> getGraY() {
        return gra_y;
    }

    public List<Float> getGraZ() {
        return gra_z;
    }

    public List<Float> getOriW() {
        return ori_w;
    }

    public List<Float> getOriX() {
        return ori_x;
    }

    public List<Float> getOriY() {
        return ori_y;
    }

    public List<Float> getOriZ() {
        return ori_z;
    }

    public List<Float> getMagneticX() {
        return magnetic_x;
    }

    public List<Float> getMagneticY() {
        return magnetic_y;
    }

    public List<Float> getMagneticZ() {
        return magnetic_z;
    }

    public void clear() {
        acc_x.clear();
        acc_y.clear();
        acc_z.clear();
        gry_x.clear();
        gry_y.clear();
        gry_z.clear();
        lin_x.clear();
        lin_y.clear();
        lin_z.clear();
        gra_x.clear();
        gra_y.clear();
        gra_z.clear();
        ori_w.clear();
        ori_x.clear();
        ori_y.clear();
        ori_z.clear();
        magnetic_x.clear();
        magnetic_y.clear();
        magnetic_z.clear();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] values = sensorEvent.values;
        int type = sensorEvent.sensor.getType();
        switch (type) {
            case Sensor.TYPE_ACCELEROMETER:
                if (acc_x.size() < 500) {
                    acc_x.add(values[0]);
                    acc_y.add(values[1]);
                    acc_z.add(values[2]);
                }
                break;
            case Sensor.TYPE_ROTATION_VECTOR:
                final float[] quaternionValue = new float[4];
                SensorManager.getQuaternionFromVector(quaternionValue, values);
                if (ori_w.size() < 500) {
                    ori_w.add(quaternionValue[0]);
                    ori_x.add(quaternionValue[1]);
                    ori_y.add(quaternionValue[2]);
                    ori_z.add(quaternionValue[3]);
                }
                break;
            case Sensor.TYPE_GYROSCOPE:
                if (gry_x.size() < 500) {
                    gry_x.add(values[0]);
                    gry_y.add(values[1]);
                    gry_z.add(values[2]);
                }
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                if (magnetic_x.size() < 500) {
                    magnetic_x.add(values[0]);
                    magnetic_y.add(values[1]);
                    magnetic_z.add(values[2]);
                }
                break;
            case Sensor.TYPE_GRAVITY:
                if (gra_x.size() < 500) {
                    gra_x.add(values[0]);
                    gra_y.add(values[0]);
                    gra_z.add(values[0]);
                }

                break;
            case Sensor.TYPE_LINEAR_ACCELERATION:
                if (lin_x.size() < 500) {
                    lin_x.add(values[0]);
                    lin_y.add(values[1]);
                    lin_z.add(values[2]);
                }
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
