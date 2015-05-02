package com.example.s9941643b.homeworktracker;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import com.example.s9941643b.homeworktracker.HomeworkContent.Homework;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class HomeworkDetailFragment extends Fragment {
    public static String ARG_ITEM_ID = "homework_id";
    private HomeworkContent.Homework mItem;
    private ImageButton mDeleteButton;
    private ImageButton mSaveButton;
    private ImageButton mAlarmButton;
    private EditText mDueDate;
    private EditText mRemindDate;
    private EditText mRemindTime;
    private EditText mNameText;
    private EditText mSubjectText;
    private DatePickerDialog mDueDateDialog;
    private DatePickerDialog mRemindDateDialog;
    private TimePickerDialog mRemindTimeDialog;
    private boolean mTwoPane;
    private int mIndex;

    private SimpleDateFormat mDateFormatter;
    private SimpleDateFormat mTimeFormatter;

    public HomeworkDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDateFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        mTimeFormatter = new SimpleDateFormat("HH:mm:ss", Locale.US);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = HomeworkContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            mIndex = HomeworkContent.ITEMS.indexOf(mItem);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_homework_detail, container, false);
        mDeleteButton = (ImageButton)rootView.findViewById(R.id.delete_homework);
        mSaveButton = (ImageButton)rootView.findViewById(R.id.save_homework);
        mAlarmButton = (ImageButton)rootView.findViewById(R.id.toggle_alarm);
        mDueDate = (EditText)rootView.findViewById(R.id.homework_detail_due);
        mRemindDate = (EditText)rootView.findViewById(R.id.homework_detail_remind);
        mRemindTime = (EditText)rootView.findViewById(R.id.homework_detail_remind_time);
        mNameText = (EditText)rootView.findViewById(R.id.homework_detail_name);
        mSubjectText = (EditText)rootView.findViewById(R.id.homework_detail_subject);

        if (mItem != null) {
            mNameText.setText(mItem.mName);
            mSubjectText.setText(mItem.mSubject);
            mDueDate.setText(mDateFormatter.format(mItem.mDateDue.getTime()));
            mRemindDate.setText(mDateFormatter.format(mItem.mDateRemind.getTime()));
            mRemindTime.setText(mTimeFormatter.format(mItem.mDateRemind.getTime()));
        }

        if (rootView.findViewById(R.id.homework_list_container) != null) {
            mTwoPane = true;
        }

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Homework Detail", mItem.mName + " Removed");

                Homework newItem;
                if (mIndex == 0) newItem = HomeworkContent.ITEMS.get(1);
                else newItem = HomeworkContent.ITEMS.get(mIndex - 1);
                HomeworkContent.ITEMS.remove(mItem);

                Iterator< Map.Entry<String, Homework> > it = HomeworkContent.ITEM_MAP.entrySet().iterator();
                for (; it.hasNext();) {
                    Map.Entry<String, Homework> entry = it.next();
                    if (entry.getKey().equals(ARG_ITEM_ID)) {
                        it.remove();
                    }
                }

                mItem = newItem;
                ARG_ITEM_ID = newItem.mID;

                Log.d("Homework Detail", mItem.mName);

                if (mTwoPane) {
                    HomeworkListFragment listFragment = (HomeworkListFragment)getFragmentManager().findFragmentById(R.id.homework_list);
                    listFragment.getAdapter().notifyDataSetChanged();
                    onCreateView(inflater, container, savedInstanceState);
                }
                else {
                    Intent intent = new Intent(getActivity(), HomeworkListActivity.class);
                    startActivity(intent);
                }
            }
        });

        mDueDateDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                mItem.mDateDue = (GregorianCalendar)newDate;
                mDueDate.setText(mDateFormatter.format(newDate.getTime()));
            }
        }, mItem.mDateDue.get(Calendar.YEAR), mItem.mDateDue.get(Calendar.MONTH), mItem.mDateDue.get(Calendar.DAY_OF_MONTH));

        mRemindDateDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                mItem.mDateRemind = (GregorianCalendar)newDate;
                mRemindDate.setText(mDateFormatter.format(newDate.getTime()));
            }
        }, mItem.mDateRemind.get(Calendar.YEAR), mItem.mDateRemind.get(Calendar.MONTH), mItem.mDateRemind.get(Calendar.DAY_OF_MONTH));

        mRemindTimeDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hour, int minute) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(mItem.mDateRemind.get(Calendar.YEAR), mItem.mDateRemind.get(Calendar.MONTH), mItem.mDateRemind.get(Calendar.DAY_OF_MONTH), hour, minute);
                mItem.mDateRemind = (GregorianCalendar)newDate;
                mRemindTime.setText(mTimeFormatter.format(newDate.getTime()));
            }
        }, mItem.mDateRemind.get(Calendar.HOUR_OF_DAY), mItem.mDateRemind.get(Calendar.MINUTE), true);

        mDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Date Picker", "Date Due Clicked");
                mDueDateDialog.show();
            }
        });

        mRemindDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Date Picker", "Date Remind Clicked");
                mRemindDateDialog.show();
            }
        });

        mRemindTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Date Picker", "Time Remind Clicked");
                mRemindTimeDialog.show();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNameText.getText().toString() != "") mItem.mName = mNameText.getText().toString();
                if (mSubjectText.getText().toString() != "") mItem.mSubject = mSubjectText.getText().toString();
                HomeworkContent.ITEMS.set(mIndex, mItem);
                HomeworkContent.ITEM_MAP.put(ARG_ITEM_ID, mItem);

                HomeworkContent.sortHomework();
                mIndex = HomeworkContent.ITEMS.indexOf(mItem);
            }
        });

        mAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItem.mAlarm) {
                    mAlarmButton.setImageResource(R.drawable.alarm_off_icon);
                    mItem.mAlarm = false;
                    HomeworkContent.ITEMS.set(mIndex, mItem);
                    HomeworkContent.ITEM_MAP.put(ARG_ITEM_ID, mItem);

                    Intent alarmIntent = new Intent(getActivity(), ReminderReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);

                    alarmManager.cancel(pendingIntent);
                }
                else {
                    mAlarmButton.setImageResource(R.drawable.alarm_icon);
                    mItem.mAlarm = true;
                    HomeworkContent.ITEMS.set(mIndex, mItem);
                    HomeworkContent.ITEM_MAP.put(ARG_ITEM_ID, mItem);

                    Intent alarmIntent = new Intent(getActivity(), ReminderReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);

                    Calendar alarmTime = mItem.mDateRemind;
                    alarmTime.setTimeZone(TimeZone.getTimeZone("UTC"));
                    alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), pendingIntent);

                    Log.d("Alarm Reminder", "" + alarmTime.getTimeInMillis());
                    Log.d("Alarm Reminder", "" + System.currentTimeMillis());
                }
            }
        });

        return rootView;
    }
}
