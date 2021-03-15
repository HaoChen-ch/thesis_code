package com.example.thesis.data;

import android.content.Context;
import android.hardware.Sensor;

import com.example.thesis.collector.OrientationDataCollector;
import com.example.thesis.collector.SensorDataCollector;

import java.util.ArrayList;
import java.util.List;

public class DataCollector {
    private SensorDataCollector acc;
    private SensorDataCollector gry;
    private SensorDataCollector lin_acc;
    private SensorDataCollector gravity;
    private SensorDataCollector pressure;
    private SensorDataCollector magnetic;
    private OrientationDataCollector ori;

    private final float[] row = new float[20];

    public DataCollector(Context context) {
        acc = new SensorDataCollector(context, Sensor.TYPE_ACCELEROMETER);
        gry = new SensorDataCollector(context, Sensor.TYPE_GYROSCOPE);
        lin_acc = new SensorDataCollector(context, Sensor.TYPE_LINEAR_ACCELERATION);
        gravity = new SensorDataCollector(context, Sensor.TYPE_GRAVITY);
        pressure = new SensorDataCollector(context, Sensor.TYPE_PRESSURE);
        ori = new OrientationDataCollector(context);
        magnetic = new SensorDataCollector(context, Sensor.TYPE_MAGNETIC_FIELD);
    }

    public boolean isSuccess() {
        return acc.isSuccess() &&
                gry.isSuccess() &&
                lin_acc.isSuccess() &&
                gravity.isSuccess() &&
                pressure.isSuccess() &&
                magnetic.isSuccess() &&
                ori.isSuccess();
    }
    public boolean size() {
        return acc.isSuccess() &&
                gry.isSuccess() &&
                lin_acc.isSuccess() &&
                gravity.isSuccess() &&
                pressure.isSuccess() &&
                magnetic.isSuccess() &&
                ori.isSuccess();
    }

    public List<Float> getAccX() {
        List<Float> data = new ArrayList<>();
        List<float[]> tmp = acc.getData();
        for (int i = 0; i < tmp.size(); i++) {
            data.add(tmp.get(i)[0]);
        }
        return data;
    }

    public List<Float> getAccY() {
        List<Float> data = new ArrayList<>();
        List<float[]> tmp = acc.getData();
        for (int i = 0; i < tmp.size(); i++) {
            data.add(tmp.get(i)[1]);
        }
        return data;
    }

    public List<Float> getAccZ() {
        List<Float> data = new ArrayList<>();
        List<float[]> tmp = acc.getData();
        for (int i = 0; i < tmp.size(); i++) {
            data.add(tmp.get(i)[2]);
        }
        return data;
    }

    public List<Float> getGryX() {
        List<Float> data = new ArrayList<>();
        List<float[]> tmp = gry.getData();
        for (int i = 0; i < tmp.size(); i++) {
            data.add(tmp.get(i)[0]);
        }
        return data;
    }

    public List<Float> getGryY() {
        List<Float> data = new ArrayList<>();
        List<float[]> tmp = gry.getData();
        for (int i = 0; i < tmp.size(); i++) {
            data.add(tmp.get(i)[1]);
        }
        return data;
    }

    public List<Float> getGryZ() {
        List<Float> data = new ArrayList<>();
        List<float[]> tmp = gry.getData();
        for (int i = 0; i < tmp.size(); i++) {
            data.add(tmp.get(i)[2]);
        }
        return data;
    }

    public List<Float> getLAccX() {
        List<Float> data = new ArrayList<>();
        List<float[]> tmp = lin_acc.getData();
        for (int i = 0; i < tmp.size(); i++) {
            data.add(tmp.get(i)[0]);
        }
        return data;
    }

    public List<Float> getLAccY() {
        List<Float> data = new ArrayList<>();
        List<float[]> tmp = lin_acc.getData();
        for (int i = 0; i < tmp.size(); i++) {
            data.add(tmp.get(i)[1]);
        }
        return data;
    }

    public List<Float> getLAccZ() {
        List<Float> data = new ArrayList<>();
        List<float[]> tmp = lin_acc.getData();
        for (int i = 0; i < tmp.size(); i++) {
            data.add(tmp.get(i)[2]);
        }
        return data;
    }

    public List<Float> getGraX() {
        List<Float> data = new ArrayList<>();
        List<float[]> tmp = gravity.getData();
        for (int i = 0; i < tmp.size(); i++) {
            data.add(tmp.get(i)[0]);
        }
        return data;
    }

    public List<Float> getGraY() {
        List<Float> data = new ArrayList<>();
        List<float[]> tmp = gravity.getData();
        for (int i = 0; i < tmp.size(); i++) {
            data.add(tmp.get(i)[1]);
        }
        return data;
    }

    public List<Float> getGraZ() {
        List<Float> data = new ArrayList<>();
        List<float[]> tmp = gravity.getData();
        for (int i = 0; i < tmp.size(); i++) {
            data.add(tmp.get(i)[2]);
        }
        return data;
    }

    public List<Float> getPressure() {
        List<Float> data = new ArrayList<>();
        List<float[]> tmp = pressure.getData();
        for (int i = 0; i < tmp.size(); i++) {
            data.add(tmp.get(i)[0]);
        }
        return data;
    }

    public List<Float> getOriW() {
        List<Float> data = new ArrayList<>();
        List<float[]> tmp = ori.getData();
        for (int i = 0; i < tmp.size(); i++) {
            data.add(tmp.get(i)[0]);
        }
        return data;
    }

    public List<Float> getOriX() {
        List<Float> data = new ArrayList<>();
        List<float[]> tmp = ori.getData();
        for (int i = 0; i < tmp.size(); i++) {
            data.add(tmp.get(i)[1]);
        }
        return data;
    }

    public List<Float> getOriY() {
        List<Float> data = new ArrayList<>();
        List<float[]> tmp = ori.getData();
        for (int i = 0; i < tmp.size(); i++) {
            data.add(tmp.get(i)[2]);
        }
        return data;
    }

    public List<Float> getOriZ() {
        List<Float> data = new ArrayList<>();
        List<float[]> tmp = ori.getData();
        for (int i = 0; i < tmp.size(); i++) {
            data.add(tmp.get(i)[3]);
        }
        return data;
    }

    public List<Float> getMagneticX() {
        List<Float> data = new ArrayList<>();
        List<float[]> tmp = magnetic.getData();
        for (int i = 0; i < tmp.size(); i++) {
            data.add(tmp.get(i)[0]);
        }
        return data;
    }

    public List<Float> getMagneticY() {
        List<Float> data = new ArrayList<>();
        List<float[]> tmp = magnetic.getData();
        for (int i = 0; i < tmp.size(); i++) {
            data.add(tmp.get(i)[1]);
        }
        return data;
    }

    public List<Float> getMagneticZ() {
        List<Float> data = new ArrayList<>();
        List<float[]> tmp = magnetic.getData();
        for (int i = 0; i < tmp.size(); i++) {
            data.add(tmp.get(i)[2]);
        }
        return data;
    }

    public void clear() {
        acc.clear();
        gry.clear();
        lin_acc.clear();
        gravity.clear();
        pressure.clear();
        ori.clear();
        magnetic.clear();

    }
}
