package com.example.diaryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.diaryapp.Fragments.DiarylistFragment;
import com.example.diaryapp.Fragments.EmojiFragment;
import com.example.diaryapp.Fragments.MoodchartFragment;
import com.example.diaryapp.Fragments.MypageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static BottomNavigationView btnNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new DiarylistFragment()).commit();

        btnNav = findViewById(R.id.bottomNavigationView);
        btnNav.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    String tag = null;

                    int itemId = item.getItemId();

                    if(itemId == R.id.diaryList) {
                        selectedFragment = new DiarylistFragment();
                        tag ="DiaryListFragment";
                    }
                    else if(itemId == R.id.myEmoji) {
                        selectedFragment = new EmojiFragment();
                        tag = "EmojiFragment";
                    }
                    else if(itemId == R.id.myMood) {
                        selectedFragment = new MoodchartFragment();
                        tag = "MoodchartFragment";
                    }
                    else if(itemId == R.id.myPage) {
                        selectedFragment = new MypageFragment();
                        tag = "MypageFragment";
                    }

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_layout, selectedFragment, tag).commit();
                    return true;
                }
            };

    public void ChangeFragment(Bundle bundle, Fragment fragment) {

        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,fragment).commit();

    }
}