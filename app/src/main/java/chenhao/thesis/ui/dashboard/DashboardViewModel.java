package chenhao.thesis.ui.dashboard;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import chenhao.thesis.collector.FeatureExtraction;
import chenhao.thesis.data.DataCollector;
import chenhao.thesis.data.Model;
import com.github.mikephil.charting.charts.LineChart;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText = new MutableLiveData<>();
    private DataCollector dataCollector;
    private FeatureExtraction featureExtraction;
    private Model model;

    public DashboardViewModel() {

    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setContext(Context context, LineChart mLineChart) {
        dataCollector = new DataCollector(context, mLineChart);
        featureExtraction = new FeatureExtraction(context);
        model = new Model(context);
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
                Float[] features;
                int maxSize = 6000;
                System.out.println("size : " + acc_x.size() + "- " + acc_y.size() + "- " + acc_z.size() + "- " +
                        o_w.size() + "- " + o_x.size() + "- " + o_y.size() + "- " + o_z.size() + "- " +
                        m_x.size() + "- " + m_y.size() + "- " + m_z.size() + "- " +
                        gy_x.size() + "- " + gy_y.size() + "- " + gy_z.size() + "- " +
                        g_x.size() + "- " + g_y.size() + "- " + g_z.size() + "- " +
                        l_x.size() + "- " + l_y.size() + "- " + l_z.size() + "- ");
                if (acc_x.size() == maxSize && o_w.size() == maxSize && m_x.size() == maxSize &&
                        gy_x.size() == maxSize && g_x.size() == maxSize && l_x.size() == maxSize) {
                    try {
                        features = featureExtraction.callPythonCode(acc_x, acc_y, acc_z,
                                o_w, o_x, o_y, o_z,
                                m_x, m_y, m_z,
                                gy_x, gy_y, gy_z,
                                g_x, g_y, g_z,
                                l_x, l_y, l_z);
                        System.out.println(Arrays.toString(features));
                        text = model.predict(features);
                    } catch (Throwable t) {
                        System.out.println(t.getMessage());
                        text = "JNI计算异常";
                    }
                } else {
                    text = "the sensor does not collect enough data";
                }
                mText.postValue(text);
                System.out.println(text);
                dataCollector.clear();
            }
        };
        timer.schedule(myTimerTask, 0, 65 * 1000L);
    }
}