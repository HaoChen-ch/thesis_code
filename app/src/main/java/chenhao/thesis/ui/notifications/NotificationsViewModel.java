package chenhao.thesis.ui.notifications;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import chenhao.thesis.data.ShowData;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.List;

public class NotificationsViewModel extends ViewModel implements SensorEventListener {

    private MutableLiveData<String> mText;
    private ShowData showData;


    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setContex(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 10000);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] values = sensorEvent.values;
        int type = sensorEvent.sensor.getType();
        List<Float> datas = new ArrayList<>(); //数据集合
        if (type == Sensor.TYPE_ACCELEROMETER) {
            datas.add(values[0]);
            datas.add(values[1]);
            datas.add(values[2]);
            showData.addEntry(datas);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    public void setChart(LineChart mLineChart) {
        showData = new ShowData(mLineChart);
    }
}