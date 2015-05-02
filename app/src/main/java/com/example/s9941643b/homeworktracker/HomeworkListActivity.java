package com.example.s9941643b.homeworktracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.s9941643b.homeworktracker.HomeworkContent.Homework;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * An activity representing a list of Homework. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link HomeworkDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link HomeworkListFragment} and the item details
 * (if present) is a {@link HomeworkDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link HomeworkListFragment.Callbacks} interface
 * to listen for item selections.
 */

public class HomeworkListActivity extends FragmentActivity implements HomeworkListFragment.Callbacks {
    private boolean mTwoPane;
    private ImageButton mHomeworkButton;
    private ImageButton mSaveAllButton;
    private HomeworkListFragment mHomeworkListFragment;
    private String mSelectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_list);

        HomeworkContent.sortHomework();
        mHomeworkButton = (ImageButton)findViewById(R.id.add_homework);
        mSaveAllButton = (ImageButton)findViewById(R.id.save_all_homework);
        mHomeworkListFragment = (HomeworkListFragment)getSupportFragmentManager().findFragmentById(R.id.homework_list);

        if (findViewById(R.id.homework_detail_container) != null) {
            mTwoPane = true;
            mHomeworkListFragment.setActivateOnItemClick(true);
        }

        mHomeworkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeworkContent.addItem(new Homework("New Homework", "Subject", new GregorianCalendar(), new GregorianCalendar()));
                mHomeworkListFragment.getAdapter().notifyDataSetChanged();
            }
        });

        mSaveAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONArray jsonStorage = new JSONArray();

                int count = 0;
                for (Homework homework : HomeworkContent.ITEMS) {
                    JSONObject json = new JSONObject();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy HH mm ss");
                    try {
                        json.put("name", homework.mName);
                        json.put("subject", homework.mSubject);
                        json.put("due_date", dateFormat.format(homework.mDateDue.getTime()));
                        json.put("remind_date", dateFormat.format(homework.mDateRemind.getTime()));
                        jsonStorage.put(count, json);
                    }
                    catch (JSONException e) {
                        //Do Nothing
                    }

                    count++;
                }

                getPreferences(0).edit().putString("data", jsonStorage.toString()).commit();
            }
        });

        if (HomeworkContent.ITEMS == new ArrayList<Homework>()) {
            String jsonString = getPreferences(0).getString("data", null);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy HH mm ss");
            if (jsonString == null) return;

            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                    Calendar dateDue = new GregorianCalendar(), dateRemind = new GregorianCalendar();

                    try {
                        dateDue.setTime(dateFormat.parse(jsonObject.getString("due_date")));
                        dateRemind.setTime(dateFormat.parse(jsonObject.getString("remind_date")));
                    }
                    catch (java.text.ParseException e) {}

                    Homework homework = new Homework(jsonObject.getString("name"), jsonObject.getString("subject"), (GregorianCalendar)dateDue, (GregorianCalendar)dateRemind);
                    HomeworkContent.addItem(homework);
                }
            }
            catch (JSONException e) {
                //Do Nothing
            }
        }
    }

    @Override
    public void onItemSelected(String id) {
        mSelectedID = id;

        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(HomeworkDetailFragment.ARG_ITEM_ID, id);
            HomeworkDetailFragment fragment = new HomeworkDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().replace(R.id.homework_detail_container, fragment).commit();
        }
        else {
            Intent detailIntent = new Intent(this, HomeworkDetailActivity.class);
            detailIntent.putExtra(HomeworkDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
