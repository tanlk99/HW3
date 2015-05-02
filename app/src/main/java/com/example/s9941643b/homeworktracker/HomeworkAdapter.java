package com.example.s9941643b.homeworktracker;

/**
 * Created by S9941643B on 4/7/2015.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.s9941643b.homeworktracker.HomeworkContent.Homework;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeworkAdapter extends ArrayAdapter<Homework> {
    private Context mContext;
    private int mResource;
    private List<Homework> mHomeworkList = new ArrayList<Homework>();

    private SimpleDateFormat mDateFormatter;

    public HomeworkAdapter(Context context, int resource, List<Homework> homeworkList) {
        super(context, resource, homeworkList);
        mContext = context;
        mResource = resource;
        mHomeworkList = homeworkList;
        mDateFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
    }

    @Override
    public View getView(int position, View row, ViewGroup parent) {
        HomeworkHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            row = inflater.inflate(mResource, parent, false);

            holder = new HomeworkHolder();
            holder.mNameView = (TextView)row.findViewById(R.id.homework_list_name);
            holder.mSubjectView = (TextView)row.findViewById(R.id.homework_list_subject);
            holder.mDateDueView = (TextView)row.findViewById(R.id.homework_list_due);
            row.setTag(holder);
        }
        else {
            holder = (HomeworkHolder)row.getTag();
        }

        Homework currentHomework = mHomeworkList.get(position);
        holder.mNameView.setText(currentHomework.mName);
        holder.mSubjectView.setText(currentHomework.mSubject);
        holder.mDateDueView.setText(mDateFormatter.format(currentHomework.mDateDue.getTime()));
        return row;
    }

    static class HomeworkHolder {
        private TextView mNameView, mSubjectView, mDateDueView;
    }
}
