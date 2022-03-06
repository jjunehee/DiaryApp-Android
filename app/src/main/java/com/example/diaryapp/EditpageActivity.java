package com.example.diaryapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.diaryapp.Database.testDbHelper;
import com.example.diaryapp.Fragments.DatePickerFragment;

public class EditpageActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView Wdate;
    TextView spinnerview;
    Spinner spinner;
    String[] emotion;

    private testDbHelper dbHelper;
    private String title;
    private String context;
    private String date;
    private String mood;
    private int isUpdate;
    private TextView ed_title;
    private TextView ed_date;
    private TextView ed_mood;
    private TextView ed_context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpage);

        Wdate = (TextView) findViewById(R.id.WtextView1);

        spinnerview = (TextView)findViewById(R.id.spinnerview);
        spinner = (Spinner)findViewById(R.id.spinner_emotion);

        spinner.setOnItemSelectedListener(this);

        emotion = new String[]{"선택하세요","행복해", "기뻐", "우울해", "화나"};

        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,emotion);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button exitbutton = findViewById(R.id.Wexitbutton); //취소 버튼
        Button savebutton = findViewById(R.id.button); //저장 버튼
        TextView dateView = (TextView) findViewById(R.id.WtextView1);
        TextView moodView = (TextView) findViewById(R.id.spinnerview);
        EditText titleView = (EditText) findViewById(R.id.WeditText);
        EditText contentView = (EditText) findViewById(R.id.editTextTextMultiLine);

        exitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //View 하나라도 비어있을 때 저장 못하게 코드 추가해야함
                String Title = titleView.getText().toString();
                String Content = contentView.getText().toString();
                String DateTmp = dateView.getText().toString(); //db에 형식 바꿔서 저장("2021-12-15")
                String MoodTmp = moodView.getText().toString();

                String year = DateTmp.substring(0, 4);
                String month = DateTmp.substring(5, 7);
                String day = DateTmp.substring(8, 10);
                String Date = year + "-" + month + "-" + day;

                int Mood = 0;

                switch (MoodTmp) {
                    case "행복해": Mood = 1;
                        break;
                    case "기뻐": Mood = 2;
                        break;
                    case "우울해": Mood = 3;
                        break;
                    case "화나": Mood = 4;
                        break;
                }

                dbHelper = new testDbHelper(getApplicationContext());
                if (isUpdate == 1 && Mood != 0) {
                    dbHelper.updateRecord(Title, Content, Date, Mood);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else if (Mood != 0) {
                    dbHelper.insertRecord(Title, Content, Date, Mood);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "기분을 정해주세요!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        ed_title = findViewById(R.id.WeditText);
        ed_context = findViewById(R.id.editTextTextMultiLine);
        ed_date = findViewById(R.id.WtextView1);
        ed_mood = findViewById(R.id.spinnerview); //수정해야됌

        Intent intent = getIntent();
        date = intent.getExtras().getString("date");
        title = intent.getExtras().getString("title");
        context = intent.getExtras().getString("context");
        int moodTmp = intent.getExtras().getInt("mood");
        isUpdate = intent.getExtras().getInt("isUpdate");

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

        ed_date.setText(date);
        ed_title.setText(title);
        ed_mood.setText(mood);
        ed_context.setText(context);

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        spinnerview.setText(emotion[i]);
        if(spinnerview.getText().toString().equals("선택하세요")){
            spinnerview.setText("");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        spinnerview.setText("");
    }

    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void processDatePickerResult(int year, int month, int day){
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        if (month_string.length() == 1 && day_string.length() == 2) {
            month_string = "0" + month_string;
        } else if (month_string.length() == 2 && day_string.length() == 1) {
            day_string = "0" + day_string;
        } else if (month_string.length() == 1 && day_string.length() == 1) {
            month_string = "0" + month_string;
            day_string = "0" + day_string;
        }

        String dateMessage = (year_string + "년" + month_string + "월" + day_string + "일");

        Toast toast = Toast.makeText(this,dateMessage+ " 선택됨",Toast.LENGTH_SHORT);
        toast.show();
        Wdate.setText(dateMessage);
    }

}
