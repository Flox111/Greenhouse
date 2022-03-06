package com.maltsev.greenhouse;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class RecyclerViewAdapterCharts extends RecyclerView.Adapter<RecyclerViewAdapterCharts.MyViewHolder> {

    private Context mContext;
    private ArrayList<Chart> mData;

    public RecyclerViewAdapterCharts(Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList<>();
        mData.add(new Chart(ChartType.AIR_TEMPERATURE));
        mData.add(new Chart(ChartType.AIR_HUMIDITY));
        mData.add(new Chart(ChartType.SOIL_HUMIDITY));
        mData.add(new Chart(ChartType.ILLUMINATION));
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_chart, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        float min = Float.MAX_VALUE, max = Float.MIN_VALUE;
        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            float t = new Random().nextFloat() % 10;
            min = t < min ? t : min;
            max = t > max ? t : max;
            float d = new Date().getTime();
            entries.add(new Entry(d, i));
            Log.d("time",Float.toString(d));
        }
        LineDataSet dataSet = new LineDataSet(entries, mData.get(position).getType().name());
        dataSet.setDrawValues(false);
        dataSet.setDrawCircles(false);
        dataSet.setLineWidth(3);

        switch (mData.get(position).getType()) {
            case AIR_TEMPERATURE:
                dataSet.setColor(ContextCompat.getColor(mContext, R.color.green4));
                holder.linearLayout.setBackgroundResource(R.drawable.chart_background1);
                mData.get(position).setTitle("Температура воздуха");
                break;
            case AIR_HUMIDITY:
                dataSet.setColor(ContextCompat.getColor(mContext, R.color.green4));
                holder.linearLayout.setBackgroundResource(R.drawable.chart_background2);
                mData.get(position).setTitle("Влажность воздуха");
                break;
            case SOIL_HUMIDITY:
                dataSet.setColor(ContextCompat.getColor(mContext, R.color.green4));
                holder.linearLayout.setBackgroundResource(R.drawable.chart_background3);
                mData.get(position).setTitle("Влажность почвы");
                break;
            case ILLUMINATION:
                dataSet.setColor(ContextCompat.getColor(mContext, R.color.green4));
                holder.linearLayout.setBackgroundResource(R.drawable.chart_background4);
                mData.get(position).setTitle("Освещенность");
                break;
        }
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        mData.get(position).setMaxValue(max);
        mData.get(position).setMinValue(min);
        LineData data = new LineData(dataSet);
        holder.lineChart.setData(data);
        holder.lineChart.animateX(800);

        holder.textViewTitle.setText(mData.get(position).getTitle());
        holder.textViewMax.setText(String.format("%.4f", mData.get(position).getMaxValue()));
        holder.textViewMin.setText(String.format("%.4f", mData.get(position).getMinValue()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewMin;
        private TextView textViewMax;
        private TextView textViewTitle;
        private LineChart lineChart;
        private LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMax = itemView.findViewById(R.id.max_value);
            textViewMin = itemView.findViewById(R.id.min_value);
            textViewTitle = itemView.findViewById(R.id.title);
            lineChart = itemView.findViewById(R.id.chart);
            linearLayout = itemView.findViewById(R.id.background);
            customizeChart(lineChart);
        }

        private void customizeChart(LineChart lineChart) {
            lineChart.setTouchEnabled(false);
            lineChart.setClickable(false);
            lineChart.setDoubleTapToZoomEnabled(false);
            lineChart.setDoubleTapToZoomEnabled(false);

            lineChart.setDrawBorders(false);
            lineChart.setDrawGridBackground(false);

            lineChart.getDescription().setEnabled(false);
            lineChart.getLegend().setEnabled(false);

            lineChart.getAxisLeft().setDrawGridLines(false);
            lineChart.getAxisLeft().setDrawLabels(false);
            lineChart.getAxisLeft().setDrawAxisLine(false);

            lineChart.getXAxis().setDrawGridLines(false);
            lineChart.getXAxis().setDrawLabels(false);
            lineChart.getXAxis().setDrawAxisLine(false);

            lineChart.getAxisRight().setDrawGridLines(false);
            lineChart.getAxisRight().setDrawLabels(false);
            lineChart.getAxisRight().setDrawAxisLine(false);
        }
    }
}
