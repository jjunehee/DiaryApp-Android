package com.example.diaryapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diaryapp.Models.DiaryData;
import com.example.diaryapp.R;
import com.example.diaryapp.ReadpageActivity;

import java.util.ArrayList;


public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.diaryViewHolder> {

    private ArrayList<DiaryData> diaryList;
    private Context context;

    public DiaryAdapter(Context context, ArrayList<DiaryData> dataSet) {
        diaryList = dataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public diaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_diary,parent,false);
        return new diaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryAdapter.diaryViewHolder viewholder, int position) {
        DiaryData diary = diaryList.get(position);
        viewholder.tv_date.setText(diary.getTime());
        viewholder.tv_title.setText(diary.getTitle());
        viewholder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return (null != diaryList ? diaryList.size() : 0);
    }

    public class diaryViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_title;
        public TextView tv_date;

        public diaryViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_date = itemView.findViewById(R.id.tv_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        String Date = diaryList.get(position).getTime();
                        String title = diaryList.get(position).getTitle();
                        String context = diaryList.get(position).getContext();
                        String mood = diaryList.get(position).getMood();
                        Intent intent = new Intent(view.getContext(), ReadpageActivity.class);
                        intent.putExtra("date", Date);
                        intent.putExtra("context", context);
                        intent.putExtra("title", title);
                        intent.putExtra("mood", mood);
                        view.getContext().startActivity(intent);
                    }
                }
            });

        }
    }
}