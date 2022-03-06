package com.example.diaryapp.Models;

public class DiaryData {
    String title;
    String Mood;
    String context;
    String time;

    public DiaryData(String title, String mood, String context, String time) {
        this.title = title;
        Mood = mood;
        this.context = context;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMood() {
        return Mood;
    }

    public void setMood(String mood) {
        Mood = mood;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}