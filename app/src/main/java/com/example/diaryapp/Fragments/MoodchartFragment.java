package com.example.diaryapp.Fragments;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.diaryapp.Database.testDbContract;
import com.example.diaryapp.Database.testDbHelper;
import com.example.diaryapp.MainActivity;
import com.example.diaryapp.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MoodchartFragment extends Fragment {
    PieChart pieChart;
    private testDbHelper dbHelper;
    private ImageButton UpMonth;
    private ImageButton DownMonth;
    private int laugh = 0;
    private int glad = 0;
    private int gloomy = 0;
    private int angry = 0;
    private String monthNow = "";
    private String yearNow = "";
    MainActivity mainActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moodchart, container, false);
        pieChart = (PieChart) view.findViewById(R.id.piechart);

        String yearAndMonth = null;
        try {
            yearAndMonth = getArguments().getString("yearAndMonth");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (yearAndMonth != null) {
            yearNow = yearAndMonth.substring(0, 4);
            monthNow = yearAndMonth.substring(5, 7);
        } else {
            long now = System.currentTimeMillis();
            Date mDate = new Date(now);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateToStr = dateFormat.format(mDate);
            yearNow = dateToStr.substring(0, 4);
            monthNow = dateToStr.substring(5, 7);
        }

        TextView text = (TextView)view.findViewById(R.id.piechart_month);
        text.setText(yearNow + "." + monthNow);

        setData();

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        yValues.add(new PieEntry(laugh, "행복함"));
        yValues.add(new PieEntry(glad, "기쁨"));
        yValues.add(new PieEntry(gloomy, "우울함"));
        yValues.add(new PieEntry(angry, "화남"));


        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic); //애니메이션

        PieDataSet dataSet = new PieDataSet(yValues, "Countries");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);

        DownMonth = view.findViewById(R.id.DownMonth);
        DownMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                laugh = 0; glad = 0; gloomy = 0; angry = 0;
                if (monthNow.equals("01")) {
                    int year = Integer.parseInt(yearNow) - 1;
                    yearNow = Integer.toString(year);
                    monthNow = "12";
                } else {
                    int month = Integer.parseInt(monthNow) - 1;
                    monthNow = Integer.toString(month);
                    if (monthNow.length() == 1)
                        monthNow = "0" + monthNow;
                }

                setData();

                ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

                yValues.add(new PieEntry(laugh, "행복함"));
                yValues.add(new PieEntry(glad, "기쁨"));
                yValues.add(new PieEntry(gloomy, "우울함"));
                yValues.add(new PieEntry(angry, "화남"));

                PieDataSet dataSet = new PieDataSet(yValues, "Countries");
                dataSet.setSliceSpace(3f);
                dataSet.setSelectionShift(5f);
                dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

                PieData data = new PieData((dataSet));
                data.setValueTextSize(10f);
                data.setValueTextColor(Color.YELLOW);

                pieChart.setData(data);

                Bundle bundle = new Bundle();
                bundle.putString("yearAndMonth", yearNow + "-" + monthNow);
                mainActivity.ChangeFragment(bundle, new MoodchartFragment());
            }
        });

        UpMonth = view.findViewById(R.id.UpMonth);
        UpMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                laugh = 0; glad = 0; gloomy = 0; angry = 0;
                if (monthNow.equals("12")) {
                    int year = Integer.parseInt(yearNow) + 1;
                    yearNow = Integer.toString(year);
                    monthNow = "01";
                } else {
                    int month = Integer.parseInt(monthNow) + 1;
                    monthNow = Integer.toString(month);
                    if (monthNow.length() == 1)
                        monthNow = "0" + monthNow;
                }

                setData();

                ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

                yValues.add(new PieEntry(laugh, "행복함"));
                yValues.add(new PieEntry(glad, "기쁨"));
                yValues.add(new PieEntry(gloomy, "우울함"));
                yValues.add(new PieEntry(angry, "화남"));

                PieDataSet dataSet = new PieDataSet(yValues, "Countries");
                dataSet.setSliceSpace(3f);
                dataSet.setSelectionShift(5f);
                dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

                PieData data = new PieData((dataSet));
                data.setValueTextSize(10f);
                data.setValueTextColor(Color.YELLOW);

                Bundle bundle = new Bundle();
                bundle.putString("yearAndMonth", yearNow + "-" + monthNow);
                mainActivity.ChangeFragment(bundle, new MoodchartFragment());
            }
        });

        return view;
    }

    public void setData() {
        dbHelper = new testDbHelper(getContext().getApplicationContext());
        Cursor cursor = dbHelper.readRecordOrderByDate();
        String yearAndMonth = yearNow + "-" + monthNow;
        Log.d("CREATION", yearAndMonth);

        while(cursor.moveToNext()) {
            String date = cursor.getString(cursor.getColumnIndexOrThrow(testDbContract.Entries.COLUMN_Date));
            if(yearAndMonth.equals(date.substring(0, 7))){
                int mood = cursor.getInt(cursor.getColumnIndexOrThrow(testDbContract.Entries.COLUMN_Mood));
                switch (mood) {
                    case 1: laugh += 1;
                        break;
                    case 2: glad += 1;
                        break;
                    case 3: gloomy += 1;
                        break;
                    case 4: angry += 1;
                        break;
                }
            }
        }
    }


}
