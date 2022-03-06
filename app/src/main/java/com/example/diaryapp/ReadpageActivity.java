package com.example.diaryapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ReadpageActivity extends Activity {
    private String title;
    private String context;
    private String date;
    private String mood;
    private TextView tv_title;
    private TextView tv_date;
    private TextView tv_mood;
    private TextView tv_context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readpage);

        tv_title = findViewById(R.id.tv_rp_title);
        tv_context = findViewById(R.id.tv_rp_context);
        tv_date = findViewById(R.id.tv_rp_date);
        tv_mood = findViewById(R.id.tv_rp_mood);

        Intent intent = getIntent();
        date = intent.getExtras().getString("date");
        title = intent.getExtras().getString("title");
        context = intent.getExtras().getString("context");
        mood = intent.getExtras().getString("mood");

        tv_date.setText(date);
        tv_title.setText(title);
        tv_mood.setText(mood);
        tv_context.setText(context);


        Button editbutton = findViewById(R.id.Reditbutton);
        Button exitbutton = findViewById(R.id.Rexitbutton);

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditpageActivity.class);
                intent.putExtra("date", date);
                intent.putExtra("context", context);
                intent.putExtra("title", title);
                int moodTmp = 0;
                switch (mood) {
                    case "행복해": moodTmp = 1;
                        break;
                    case "기뻐": moodTmp = 2;
                        break;
                    case "우울해": moodTmp = 3;
                        break;
                    case "화나": moodTmp = 4;
                        break;
                }
                intent.putExtra("mood", moodTmp);
                intent.putExtra("isUpdate", 1);
                startActivity(intent);
            }
        });

        exitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

}