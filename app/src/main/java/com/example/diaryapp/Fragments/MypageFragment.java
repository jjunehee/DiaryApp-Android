package com.example.diaryapp.Fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.diaryapp.Database.testDbHelper;
import com.example.diaryapp.R;

public class MypageFragment extends Fragment {

    public ImageView iv_profile;
    private static final int GET_GALLERY_IMAGE=200;
    public TextView tv_num;
    private testDbHelper dbHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        tv_num = (TextView) view.findViewById(R.id.tv_num);
        iv_profile = (ImageView) view.findViewById(R.id.iv_profile);
        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        String count;
        dbHelper = new testDbHelper(getContext().getApplicationContext());
        count = dbHelper.count();
        tv_num.setText(count);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            iv_profile.setImageURI(selectedImageUri);
        }
    }
}
