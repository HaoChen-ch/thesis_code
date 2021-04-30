package chenhao.thesis.data;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ShowData {
    private LineChart mLineChart;
    private List<String> mTimeList = new ArrayList<>(); //存储x轴的时间
    private LineDataSet mLineDataSet;
    private List<ILineDataSet> mLineDataSets = new ArrayList<>();//多条折线
    private LineData mLineData;
    private YAxis mLeftAxis;
    private YAxis mRightAxis;
    private XAxis mxAxis;
    private SimpleDateFormat df = new SimpleDateFormat("mm:ss");//设置日期格式 

    public ShowData(LineChart mLineChart) {
        this.mLineChart = mLineChart;
        mLeftAxis = mLineChart.getAxisLeft();
        mRightAxis = mLineChart.getAxisRight();
        mxAxis = mLineChart.getXAxis();
        initLineChart();
        initLineDataSet();
        setDescription("加速度传感器数据展示");
        setYAxis(12, -12, 10);
    }


    private void initLineChart() {
        mLineChart.setDrawGridBackground(false);
        //显示边界
        mLineChart.setDrawBorders(true);
        //折线图例 标签 设置
        Legend legend = mLineChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(9f);
        //显示位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);

        //X轴设置显示位置在底部
        mxAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        mxAxis.setGranularity(5f);//多少个点显示一个label


        mxAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mTimeList.get((int) value % mTimeList.size());
            }
        });

        //保证Y轴从0开始，不然会上移一点

        mLeftAxis.setAxisMinimum(0f);
        mLeftAxis.setGranularity(2f);
        mRightAxis.setAxisMinimum(0f);
        mRightAxis.setGranularity(2f);
    }


    /**
     * 初始化折线（多条线）
     */
    private void initLineDataSet() {
        List<String> names = new ArrayList<>(); //折线名字集合
        List<Integer> colors = new ArrayList<>();//折线颜色集合

        //折线名字
        names.add("x轴");
        names.add("y轴");
        names.add("z轴");

        //折线颜色
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.RED);

        for (int i = 0; i < names.size(); i++) {
            mLineDataSet = new LineDataSet(null, names.get(i));
            mLineDataSet.setColor(colors.get(i));
            mLineDataSet.setLineWidth(1.5f);
            mLineDataSet.setCircleRadius(1.5f);
            mLineDataSet.setColor(colors.get(i));

            mLineDataSet.setDrawFilled(true);
            mLineDataSet.setCircleColor(colors.get(i));
            mLineDataSet.setHighLightColor(colors.get(i));
            mLineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            mLineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            //mLineDataSet.setValueTextSize(9f);
            mLineDataSet.setDrawValues(false);//不显示曲线上的数值

            mLineDataSets.add(mLineDataSet);

        }
        //添加一个空的 LineData
        mLineData = new LineData();
        mLineChart.setData(mLineData);
        mLineChart.invalidate();
    }

    public void setDescription(String str) {
        Description description = new Description();
        description.setText(str);
        mLineChart.setDescription(description);

        mLineChart.invalidate();
    }

    public void setYAxis(float max, float min, int labelCount) {
        if (max < min) {
            return;
        }
        mLeftAxis.setAxisMaximum(max);
        mLeftAxis.setAxisMinimum(min);
        mLeftAxis.setLabelCount(labelCount, false);
        mRightAxis.setAxisMaximum(max);
        mRightAxis.setAxisMinimum(min);
        mRightAxis.setLabelCount(labelCount, false);
        mLineChart.invalidate();
    }

    public void addEntry(List<Float> numbers) {

        if (mLineDataSets.get(0).getEntryCount() == 0) {
            mLineData = new LineData(mLineDataSets);
            mLineChart.setData(mLineData);
        }

        mTimeList.add(df.format(System.currentTimeMillis()));

        for (int i = 0; i < numbers.size(); i++) { //依次更新每条曲线
            Entry entry = new Entry(mLineDataSet.getEntryCount(), numbers.get(i));
            mLineData.addEntry(entry, i);
            mLineData.notifyDataChanged();
            mLineChart.notifyDataSetChanged();
            mLineChart.setVisibleXRangeMaximum(25); //一条曲线显示多少个数据点
            mLineChart.moveViewToX(mLineData.getEntryCount() - 5);
        }

    }
}
