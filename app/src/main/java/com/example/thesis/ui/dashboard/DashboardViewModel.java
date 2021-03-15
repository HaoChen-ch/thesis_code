package com.example.thesis.ui.dashboard;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.thesis.collector.Compute;
import com.example.thesis.data.DataCollector;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText = new MutableLiveData<>();
    private DataCollector dataCollector;

    private Context context;
    private Compute compute;

    public DashboardViewModel() {
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setContext(Context context) {
        this.context = context;
        dataCollector = new DataCollector(context);
        compute = new Compute(context);
    }

    public void beginService() {
        startService();
    }

    public void startService() {
        Timer timer = new Timer();
        TimerTask myTimerTask = new TimerTask() {
            @Override
            public void run() {
                String text = "no";
                List acc_x = dataCollector.getAccX();
                List acc_y = dataCollector.getAccY();
                List acc_z = dataCollector.getAccZ();
                List o_w = dataCollector.getOriW();
                List o_x = dataCollector.getOriX();
                List o_y = dataCollector.getOriY();
                List o_z = dataCollector.getOriZ();
                List m_x = dataCollector.getMagneticX();
                List m_y = dataCollector.getMagneticY();
                List m_z = dataCollector.getMagneticZ();
                List gy_x = dataCollector.getGryX();
                List gy_y = dataCollector.getGryY();
                List gy_z = dataCollector.getGryZ();
                List g_x = dataCollector.getGraX();
                List g_y = dataCollector.getGraY();
                List g_z = dataCollector.getGraZ();
                List l_x = dataCollector.getLAccX();
                List l_y = dataCollector.getLAccY();
                List l_z = dataCollector.getLAccZ();
                List pressure = dataCollector.getPressure();

                System.out.println("size : " + acc_x.size() + "- " + acc_y.size() + "- " + acc_z.size() + "- " +
                        o_w.size() + "- " + o_x.size() + "- " + o_y.size() + "- " + o_z.size() +"- "+
                        m_x.size() +"- "+ m_y.size() +"- "+ m_z.size() +"- "+
                        gy_x.size() +"- "+ gy_y.size() +"- "+ gy_z.size() +"- "+
                        g_x.size() +"- "+ g_y.size() +"- "+ g_z.size() +"- "+
                        l_x.size() +"- "+ l_y.size() +"- "+ l_z.size() +"- "+
                        pressure.size());
                if (acc_x.size() == 500 && acc_y.size() == 500 && acc_z.size() == 500 &&
                        o_w.size() == 500 && o_x.size() == 500 && o_y.size() == 500 && o_z.size() == 500 &&
                        m_x.size() == 500 && m_y.size() == 500 && m_z.size() == 500 &&
                        gy_x.size() == 500 && gy_y.size() == 500 && gy_z.size() == 500 &&
                        g_x.size() == 500 && g_y.size() == 500 && g_z.size() == 500 &&
                        l_x.size() == 500 && l_y.size() == 500 && l_z.size() == 500 &&
                        pressure.size() == 500) {
                    text = compute.callPythonCode(acc_x, acc_y, acc_z,
                            o_w, o_x, o_y, o_z,
                            m_x, m_y, m_z,
                            gy_x, gy_y, gy_z,
                            g_x, g_y, g_z,
                            l_x, l_y, l_z, pressure);
                } else {
                    text = String.valueOf(System.currentTimeMillis());
                }
                mText.postValue(text);
                dataCollector.clear();
            }
        };
        timer.schedule(myTimerTask, 0, 5000L);
    }
}