package com.example.diaryapp.Fragments;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.diaryapp.Database.testDbContract;
import com.example.diaryapp.Database.testDbHelper;
import com.example.diaryapp.EditpageActivity;
import com.example.diaryapp.MainActivity;
import com.example.diaryapp.R;
import com.example.diaryapp.ReadpageActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EmojiFragment extends Fragment {
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private testDbHelper dbHelper;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("Range")
    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emoji, container, false);
        CalendarView calendarView = (CalendarView) view.findViewById(R.id.calendarView); //달력 전체
        List<EventDay> events = new ArrayList<>(); //event(emoji)목록

        dbHelper = new testDbHelper(getContext().getApplicationContext());
        Cursor cursor = dbHelper.readRecordOrderByDate();
        while(cursor.moveToNext()) {
            String date = cursor.getString(cursor.getColumnIndexOrThrow(testDbContract.Entries.COLUMN_Date));
            String year = date.substring(0, 4);
            String month = date.substring(5, 7);
            String day = date.substring(8, 10);
            Log.d("CREATION", year + "-" + month + "-" + day);
            int mood = cursor.getInt(cursor.getColumnIndexOrThrow(testDbContract.Entries.COLUMN_Mood));

            Calendar calendar = Calendar.getInstance();
            switch (mood) {
                case 1: events.add(new EventDay(calendar, R.drawable.laugh1));
                    break;
                case 2: events.add(new EventDay(calendar, R.drawable.glad));
                    break;
                case 3: events.add(new EventDay(calendar, R.drawable.gloomy));
                    break;
                case 4: events.add(new EventDay(calendar, R.drawable.angry));
                    break;
            }
            calendar.set(Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt(day)); //month는 1빼야함
            calendarView.setEvents(events);
        }

        //click 이벤트
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
                Date date = clickedDayCalendar.getTime();
                String dateToStr = dateFormat.format(date);
                Log.d("CREATION", dateToStr);

                dbHelper = new testDbHelper(getContext().getApplicationContext());
                Cursor cursor = dbHelper.readRecordByDate(dateToStr.replace(".", "-")); //비교를 위해 db저장 형식에 맞춰야 함
                if(cursor.moveToNext()) { //일기가 있는 날이면 ReadPageActivity로
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(testDbContract.Entries.COLUMN_Title));
                    String content = cursor.getString(cursor.getColumnIndexOrThrow(testDbContract.Entries.COLUMN_Content));
                    String mood = cursor.getString(cursor.getColumnIndexOrThrow(testDbContract.Entries.COLUMN_Mood));
                    switch (Integer.parseInt(mood)) {
                        case 1: mood = "행복해";
                            break;
                        case 2: mood = "기뻐";
                            break;
                        case 3: mood = "우울해";
                            break;
                        case 4: mood = "화나";
                            break;
                    }

                    Intent intent = new Intent(view.getContext(), ReadpageActivity.class);
                    intent.putExtra("date", dateToStr);
                    intent.putExtra("context", content);
                    intent.putExtra("title", title);
                    intent.putExtra("mood", mood);
                    view.getContext().startActivity(intent);
                } else { //일기가 없는 날이면 EditpageActivity로
                    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy년MM월dd일");
                    String dateToEdit = simpleDate.format(date);
                    Intent intent = new Intent(view.getContext(), EditpageActivity.class);
                    intent.putExtra("date", dateToEdit);
                    intent.putExtra("context", ' ');
                    intent.putExtra("title", ' ');
                    intent.putExtra("mood", ' ');
                    startActivity(intent);
                }
            }
        });

        return view;
    }
}
