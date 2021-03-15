package com.example.thesis.data;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.thesis.collector.SensorDataCollector;

import java.util.TimerTask;

public class MyTimerTask extends TimerTask {
    private MutableLiveData<String> mText;
    public long begin = System.currentTimeMillis();
    private Context context;

    public MyTimerTask(MutableLiveData<String> mText, Context context) {
        this.mText = mText;
        this.context = context;
    }

    @Override
    public void run() {
//        SensorDataCollector accDataCollector = new SensorDataCollector(context);
//        System.out.println((System.currentTimeMillis() - begin) / 1000);
//        begin = System.currentTimeMillis();
//        mText.postValue(String.valueOf(begin));
    }
}
