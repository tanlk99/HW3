package com.example.s9941643b.homeworktracker;

import android.app.DatePickerDialog;
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

import com.example.s9941643b.homeworktracker.HomeworkContent.Homework;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/**
 * A fragment representing a single Homework detail screen.
 * This fragment is either contained in a {@link HomeworkListActivity}
 * in two-pane mode (on tablets) or a {@link HomeworkDetailActivity}
 * on handsets.
 */
public class HomeworkDetailFragment extends Fragment {
    public static String ARG_ITEM_ID = "homework_id";
    private HomeworkContent.Homework mItem;
    private ImageButton mDeleteButton;
    private EditText mDueDate;
    private EditText mRemindDate;
    private DatePickerDialog mDueDateDialog;
    private DatePickerDialog mRemindDateDialog;
    private boolean mTwoPane;

    private SimpleDateFormat mDateFormatter;

    public HomeworkDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDateFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = HomeworkContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_homework_detail, container, false);
        mDeleteButton = (ImageButton)rootView.findViewById(R.id.delete_homework);
        mDueDate = (EditText)rootView.findViewById(R.id.homework_detail_due);
        mRemindDate = (EditText)rootView.findViewById(R.id.homework_detail_remind);

        if (mItem != null) {
            ((EditText)rootView.findViewById(R.id.homework_detail_name)).setText(mItem.mName);
            ((EditText)rootView.findViewById(R.id.homework_detail_subject)).setText(mItem.mSubject);
            mDueDate.setText(mDateFormatter.format(mItem.mDateDue.getTime()));
            mRemindDate.setText(mDateFormatter.format(mItem.mDateDue.getTime()));
        }

        if (rootView.findViewById(R.id.homework_list_container) != null) {
            mTwoPane = true;
        }

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Homework Detail", mItem.mName + " Removed");

                Homework newItem;
                if (HomeworkContent.ITEMS.indexOf(mItem) == 0) newItem = HomeworkContent.ITEMS.get(1);
                else newItem = HomeworkContent.ITEMS.get(HomeworkContent.ITEMS.indexOf(mItem) - 1);
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

        Calendar newCalendar = Calendar.getInstance();
        mDueDateDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                mItem.mDateDue = (GregorianCalendar)newDate;
                mDueDate.setText(mDateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        mRemindDateDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                if (newDate.compareTo((Calendar)mItem.mDateDue) > 0) {
                    newDate = mItem.mDateDue;
                }

                mItem.mDateRemind = (GregorianCalendar)newDate;
                mRemindDate.setText(mDateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

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

        return rootView;
    }
}
