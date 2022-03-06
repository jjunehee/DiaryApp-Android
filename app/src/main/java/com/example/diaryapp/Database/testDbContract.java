package com.example.diaryapp.Database;

import android.provider.BaseColumns;

public final class testDbContract {
    private testDbContract() {
    }

    // 하나의 테이블에 필요한 내용을 하나의 클래스에 정의한다.
    public static class Entries implements BaseColumns {
        public static final String TABLE_NAME = "test";
        public static final String COLUMN_Title = "title";
        public static final String COLUMN_Content = "content";
        public static final String COLUMN_Date = "date";
        public static final String COLUMN_Mood = "mood";
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                        COLUMN_Title + " TEXT," +
                        COLUMN_Content + " TEXT," +
                        COLUMN_Date + " DATE PRIMARY KEY," +
                        COLUMN_Mood + " INTEGER)";
        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}