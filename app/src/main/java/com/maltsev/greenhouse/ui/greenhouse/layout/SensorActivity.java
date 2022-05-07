package com.maltsev.greenhouse.ui.greenhouse.layout;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.maltsev.greenhouse.R;
import com.maltsev.greenhouse.ui.greenhouse.MyMarkerView;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class SensorActivity extends AppCompatActivity {

    private LineChart chart;

    private TextView minValue;
    private TextView maxValue;
    private TextView avgValue;

    private float min;
    private float max;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sensor);

        Toolbar toolbar = findViewById(R.id.toolbar_sensor);
        toolbar.setTitle(getIntent().getExtras().get("name").toString());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        maxValue = findViewById(R.id.max_value);
        minValue = findViewById(R.id.min_value);
        avgValue = findViewById(R.id.avg_value);

        {
            chart = findViewById(R.id.chart1);
            chart.getDescription().setEnabled(false);
            chart.setTouchEnabled(true);

            MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
            mv.setChartView(chart);
            chart.setMarker(mv);

            chart.setDragEnabled(true);
            chart.setScaleEnabled(true);
            chart.setScaleXEnabled(true);
            chart.setScaleYEnabled(true);

            chart.setPinchZoom(true);
        }

        XAxis xAxis;
        {
            xAxis = chart.getXAxis();
            xAxis.setTextColor(ContextCompat.getColor(this, R.color.green4));
            xAxis.enableGridDashedLine(10f, 10f, 0f);
            xAxis.setValueFormatter(new ValueFormatter() {

                private final SimpleDateFormat mFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

                @Override
                public String getFormattedValue(float value) {
                    long millis = TimeUnit.MINUTES.toMillis((long) value);
                    return mFormat.format(new Date(millis));
                }
            });
        }

        YAxis yAxis;
        {
            yAxis = chart.getAxisLeft();
            yAxis.setTextColor(ContextCompat.getColor(this, R.color.green4));
            chart.getAxisRight().setEnabled(false);
            yAxis.enableGridDashedLine(10f, 10f, 0f);
            yAxis.setAxisMaximum(170f);
            yAxis.setAxisMinimum(-40f);
        }

        setData();
        chart.animateX(1500);

        yAxis.setAxisMaximum(max + 5f);
        yAxis.setAxisMinimum(min - 5f);

        Legend l = chart.getLegend();
        l.setTextSize(15);
        l.setTextColor(ContextCompat.getColor(this, R.color.green4));
        l.setForm(Legend.LegendForm.LINE);
    }

    private void setData() {
        min = Float.MAX_VALUE;
        max = Float.MIN_VALUE;
        ArrayList<Entry> values = new ArrayList<>();

        float sum = 0f, count = 0f;
        for (float x = 0; x <= 1440; x += 30) {
            float val = (float) (Math.random() * 100) + 50;
            min = val < min ? val : min;
            max = val > max ? val : max;
            sum += val;
            count++;
            values.add(new Entry(x, val));
        }

        maxValue.setText(String.format("%.4f", max));
        minValue.setText(String.format("%.4f", min));
        avgValue.setText(String.format("%.4f", (sum / count)));

        LineDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.notifyDataSetChanged();
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, getIntent().getExtras().get("title").toString());

            set1.setDrawIcons(false);

            set1.setColor(ContextCompat.getColor(this, R.color.green4));
            set1.setCircleColor(ContextCompat.getColor(this, R.color.green4));

            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);

            set1.setDrawCircleHole(true);

            set1.setFormLineWidth(2f);
            set1.setFormSize(15.f);

            set1.setValueTextSize(10f);
            set1.setValueTextColor(ContextCompat.getColor(this, R.color.green4));

            set1.enableDashedHighlightLine(10f, 5f, 0f);

            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            if (Utils.getSDKInt() >= 18) {
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            chart.setData(data);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
