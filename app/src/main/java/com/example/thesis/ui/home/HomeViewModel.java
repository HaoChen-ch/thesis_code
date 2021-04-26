package com.example.thesis.ui.home;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.Collections;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    static final String TAG = "PythonOnAndroid";
    private final Application application;
    private MutableLiveData<String> mText = new MutableLiveData<>();;

    public HomeViewModel(Application application) {
        super(application);
        this.application = application;
    }

    void initPython() {
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(application.getApplicationContext()));
        }
    }

    void callPythonCode() {
        Python py = Python.getInstance();
        List a = Collections.singletonList(new int[]{1, 2, 3, 4, 6});

        PyObject obj1 = py.getModule("test").callAttr("add", a.toArray());
        // 将Python返回值换为Java中的Integer类型
        Double sum = obj1.toJava(Double.class);
        System.out.println("++++++++++" + sum.toString());
        mText.setValue("add = " + sum.toString());

    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setContext(Context context) {
        application.getApplicationContext();
    }
}