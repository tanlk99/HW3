package com.example.s9941643b.homeworktracker;

/**
 * Created by S9941643B on 4/7/2015.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.s9941643b.homeworktracker.HomeworkContent.Homework;

import java.util.ArrayList;
import java.util.List;

public class HomeworkAdapter extends ArrayAdapter<Homework> {
    private Context mContext;
    private int mResource;
    private List<Homework> mHomeworkList = new ArrayList<Homework>();

    public HomeworkAdapter(Context context, int resource, List<Homework> homeworkList) {
        super(context, resource, homeworkList);
        mResource = resource;
        mHomeworkList = homeworkList;
    }

    @Override
    public View getView(int position, View row, ViewGroup parent) {
        HomeworkHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            row = inflater.inflate(mResource, parent, false);

            holder = new HomeworkHolder();

        }
        else {
            holder = (HomeworkHolder)row.getTag();

        }
        Homework currentHomework = mHomeworkList.get(position);
        return row;
    }

    static class HomeworkHolder {
        private TextView mNameView, mSubjectView, mDateDueView;
        private ImageView mDoneIcon;
    }
}
