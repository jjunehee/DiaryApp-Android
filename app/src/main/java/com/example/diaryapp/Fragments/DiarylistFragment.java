package com.example.diaryapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.EventDay;
import com.example.diaryapp.Adapters.DiaryAdapter;
import com.example.diaryapp.Database.testDbContract;
import com.example.diaryapp.Database.testDbHelper;
import com.example.diaryapp.EditpageActivity;
import com.example.diaryapp.Models.DiaryData;
import com.example.diaryapp.R;
import com.example.diaryapp.ReadpageActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DiarylistFragment extends Fragment {

    private testDbHelper dbHelper;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<DiaryData> diaryList = new ArrayList<DiaryData>();
    private DiaryAdapter mdiaryAdapter;
    private ImageButton writeButton;
    private TextView tv_mydiary;
    private Context context;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diarylist, container, false);

        context = container.getContext();

        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy년MM월dd일");
        String getTime = simpleDate.format(mDate);

        writeButton = view.findViewById(R.id.writeButton);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String getTime = dateFormat.format(mDate);
                dbHelper = new testDbHelper(getContext().getApplicationContext());
                Cursor cursor = dbHelper.readRecordByDate(getTime);
                if (cursor.moveToNext()) { //이미 오늘 일기를 썼다면 true
                    String content = cursor.getString(cursor.getColumnIndexOrThrow(testDbContract.Entries.COLUMN_Content));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(testDbContract.Entries.COLUMN_Title));
                    int mood = cursor.getInt(cursor.getColumnIndexOrThrow(testDbContract.Entries.COLUMN_Mood));
                    Log.d("CREATION", "성공");

                    Intent intent = new Intent(getActivity(), EditpageActivity.class);
                    intent.putExtra("date", getTime);
                    intent.putExtra("context", content);
                    intent.putExtra("title", title);
                    intent.putExtra("mood", mood);
                    intent.putExtra("isUpdate", 1);
                    startActivity(intent);
                } else {
                    Log.d("CREATION", "실패");
                    Intent intent = new Intent(getActivity(), EditpageActivity.class);
                    intent.putExtra("date", getTime);
                    intent.putExtra("context", ' ');
                    intent.putExtra("title", ' ');
                    intent.putExtra("mood", ' ');
                    startActivity(intent);
                }
            }
        });

        tv_mydiary = view.findViewById(R.id.tv_mydiarytitle);

        mRecyclerView = view.findViewById(R.id.rv_diary);

        setData();
        RecyclerViewSettings();

        return view;
    }

    private void RecyclerViewSettings() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mdiaryAdapter = new DiaryAdapter(getActivity(), diaryList);
        mRecyclerView.setAdapter(mdiaryAdapter);
    }

    private void setData() {
        dbHelper = new testDbHelper(getContext().getApplicationContext());
        Cursor cursor = dbHelper.readRecordOrderByDate();
        while(cursor.moveToNext()) {
            String dateTmp = cursor.getString(cursor.getColumnIndexOrThrow(testDbContract.Entries.COLUMN_Date));
            String year = dateTmp.substring(0, 4);
            String month = dateTmp.substring(5, 7);
            String day = dateTmp.substring(8, 10);
            Log.d("CREATION", year + "-" + month + "-" + day);

            String date = year + "." + month + "." + day;
            String title = cursor.getString(cursor.getColumnIndexOrThrow(testDbContract.Entries.COLUMN_Title));
            String content = cursor.getString(cursor.getColumnIndexOrThrow(testDbContract.Entries.COLUMN_Content));
            String mood = "";
            int moodTmp = cursor.getInt(cursor.getColumnIndexOrThrow(testDbContract.Entries.COLUMN_Mood));

            switch (moodTmp) {
                case 1: mood = "행복해";
                    break;
                case 2: mood = "기뻐";
                    break;
                case 3: mood = "우울해";
                    break;
                case 4: mood = "화나";
                    break;
            }

            diaryList.add(new DiaryData(title, mood, content, date));
        }
    }
}