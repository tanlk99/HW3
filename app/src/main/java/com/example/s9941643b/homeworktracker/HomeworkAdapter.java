package com.example.s9941643b.homeworktracker;

/**
 * Created by S9941643B on 4/7/2015.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.s9941643b.homeworktracker.HomeworkContent.Homework;

import java.util.List;

public class HomeworkAdapter extends ArrayAdapter<Homework> {
    private int mResource;
    private List<Homework> mHomeworkList;

    public HomeworkAdapter(Context context, int resource, List<Homework> homeworkList) {
        super(context, resource, homeworkList);
        mResource = resource;
        mHomeworkList = homeworkList;
    }

    @Override
    public View getView(int position, View row, ViewGroup parent) {
        if (row == null) {
            row = getLayoutInflater().inflate(mResource, parent, false);
        }
    }
}
