package com.example.thesis.ui.home;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HomeViewModel extends ViewModel {
    static final String TAG = "PythonOnAndroid";
    private MutableLiveData<String> mText;
    Context context;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
    }

    void initPython() {
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this.context));
        }
    }

    void callPythonCode() {
        Python py = Python.getInstance();
        List a = Collections.singletonList(new int[]{1, 2, 3,4,6});

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
        this.context = context;

    }
}