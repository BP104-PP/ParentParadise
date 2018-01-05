package com.project.pp.parentparadise.amber;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.project.pp.parentparadise.R;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    private static final String TAG = "FavoriteFragment";
    private PieChart pieChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorite_main, container, false);
        pieChart = view.findViewById(R.id.pieChart);

        /* 設定可否旋轉 */
        pieChart.setRotationEnabled(true);

        /* 設定圓心文字 */
        pieChart.setCenterText("我的收藏");
        /* 設定圓心文字大小 */
        pieChart.setCenterTextSize(25);
        Description description = new Description();
        description.setText("");
        description.setTextSize(25);
        pieChart.setDescription(description);
        pieChart.animateXY(1000, 1000);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setHoleRadius(40f);
        pieChart.setTransparentCircleRadius(50f);


        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, Highlight highlight) {
                PieEntry pieEntry = (PieEntry) entry;
                String text = pieEntry.getLabel();
                Intent intent;

                switch (text){
                    case "甜蜜家庭":
                        intent = new Intent(getActivity(), FavoriteFragmentSweet.class);
                        startActivity(intent);
                        break;
                    case "親子活動":
                        intent = new Intent(getActivity(), FavoriteFragmentAct.class);
                        startActivity(intent);
                        break;
                    case "閱讀":
                        intent = new Intent(getActivity(), FavoriteFragmentBook.class);
                        startActivity(intent);
                        break;
                    case "影音":
                        intent = new Intent(getActivity(), FavoriteFragmentMedia.class);
                        startActivity(intent);
                        break;
                    case "生活":
                        intent = new Intent(getActivity(), FavoriteFragmentLife.class);
                        startActivity(intent);
                        break;
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        /* 取得各類別收藏資料 */
        List<PieEntry> pieEntries = getFavoriteEntries();

        PieDataSet pieDataSet = new PieDataSet(pieEntries,"");
        pieDataSet.setValueTextColor(Color.alpha(0));
        pieDataSet.setValueTextSize(20);
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(20);
        pieDataSet.setValueFormatter(new MyValueFormatter());

        int[] My_COLORS = {
                Color.rgb(135, 206, 235),
                Color.rgb(192, 156, 245),
                Color.rgb(255, 182, 193),
                Color.rgb(250, 224, 95),
                Color.rgb(179, 238, 58),
        };

        /* 使用官訂顏色範本，顏色不能超過5種，否則官定範本要加顏色 */
        pieDataSet.setColors(My_COLORS);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
        return view;
    }


    public class MyValueFormatter implements IValueFormatter {

        private String mFormat;

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            String mFormat = String.valueOf(value);
            return mFormat;
        }
    }
    private List<PieEntry> getFavoriteEntries() {
        List<PieEntry> favoriteEntries = new ArrayList<>();
        favoriteEntries.add(new PieEntry(1, "甜蜜家庭"));
        favoriteEntries.add(new PieEntry(1, "親子活動"));
        favoriteEntries.add(new PieEntry(1, "閱讀"));
        favoriteEntries.add(new PieEntry(1, "影音"));
        favoriteEntries.add(new PieEntry(1, "生活"));
        return favoriteEntries;
    }

    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.llContent, fragment);
        fragmentTransaction.commit();
    }

}