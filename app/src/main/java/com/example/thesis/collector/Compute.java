package com.example.thesis.collector;

import android.content.Context;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Compute {
    private Context context;

    public Compute(Context context) {
        this.context = context;
        initPython();
    }

    void initPython() {
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this.context));
        }
    }

    public String callPythonCode(List acc_x, List acc_y, List acc_z, List o_w, List o_x, List o_y, List o_z, List m_x, List m_y, List m_z, List gy_x, List gy_y, List gy_z, List g_x, List g_y, List g_z, List l_x, List l_y, List l_z,
                          List pressure) {
        Python py = Python.getInstance();
        PyObject obj1 = py.getModule("forJava").callAttr("fun1",
                acc_x.toArray(), acc_y.toArray(), acc_z.toArray()
                , o_w.toArray(), o_x.toArray(), o_y.toArray(), o_z.toArray(), m_x.toArray()
                , m_y.toArray(), m_z.toArray(), gy_x.toArray(), gy_y.toArray(), gy_z.toArray()
                , g_x.toArray(), g_y.toArray(), g_z.toArray(), l_x.toArray(), l_y.toArray(), l_z.toArray(),
                pressure.toArray());
        Object[] sum = obj1.toJava(Object[].class);
        return Arrays.toString(sum);
    }
}
