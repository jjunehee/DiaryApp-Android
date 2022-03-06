package com.example.diaryapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;


public class testDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "database";
    public static final int DATABASE_VERSION = 1;

    public testDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(testDbContract.Entries.SQL_DELETE_TABLE);
        sqLiteDatabase.execSQL(testDbContract.Entries.SQL_CREATE_TABLE); // 테이블 생성
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // 단순히 데이터를 삭제하고 다시 시작하는 정책이 적용될 경우
        sqLiteDatabase.execSQL(testDbContract.Entries.SQL_DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void insertRecord(String title, String content, String date, Integer mood) {
        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(testDbContract.Entries.COLUMN_Title, title);
        values.put(testDbContract.Entries.COLUMN_Content, content);
        values.put(testDbContract.Entries.COLUMN_Date, date);
        values.put(testDbContract.Entries.COLUMN_Mood, mood);

        try {
            db.insert(testDbContract.Entries.TABLE_NAME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void updateRecord(String title, String content, String date, Integer mood) {
        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(testDbContract.Entries.COLUMN_Title, title);
        values.put(testDbContract.Entries.COLUMN_Content, content);
        values.put(testDbContract.Entries.COLUMN_Date, date);
        values.put(testDbContract.Entries.COLUMN_Mood, mood);
        String[] params = {date};

        try {
            db.update(testDbContract.Entries.TABLE_NAME, values,"date=?", params);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void deleteRecord() {
        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        //db.delete(testDbContract.Entries.TABLE_NAME, null, );
    }

    public Cursor readRecordOrderByDate() { //날짜 오름차순으로 데이터 출력
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                testDbContract.Entries.COLUMN_Title,
                testDbContract.Entries.COLUMN_Content,
                testDbContract.Entries.COLUMN_Date,
                testDbContract.Entries.COLUMN_Mood,
        };

        String sortOrder = testDbContract.Entries.COLUMN_Date + " DESC";

        Cursor cursor = db.query(
                testDbContract.Entries.TABLE_NAME,   // The table to query
                projection,   // The array of columns to return (pass null to get all)
                null,   // where 문에 필요한 column
                null,   // where 문에 필요한 value
                null,   // group by를 적용할 column
                null,   // having 절
                sortOrder   // 정렬 방식
        );

        return cursor;
    }
    public Cursor readRecordByDate(String date) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                testDbContract.Entries.COLUMN_Title,
                testDbContract.Entries.COLUMN_Content,
                testDbContract.Entries.COLUMN_Date,
                testDbContract.Entries.COLUMN_Mood,
        };

        String[] params={date};

        Cursor cursor = db.query(
                testDbContract.Entries.TABLE_NAME,   // The table to query
                projection,   // The array of columns to return (pass null to get all)
                "date=?",   // where 문에 필요한 column
                params,   // where 문에 필요한 value
                null,   // group by를 적용할 column
                null,   // having 절
                null   // 정렬 방식
        );

        return cursor;
    }

    public String count() {
        SQLiteDatabase db = getReadableDatabase();
        long num = DatabaseUtils.queryNumEntries(db,testDbContract.Entries.TABLE_NAME);
        String count = String.valueOf(num);
        return count;
    }
}