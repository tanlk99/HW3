package com.example.s9941643b.homeworktracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.s9941643b.homeworktracker.HomeworkContent.Homework;

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
    private ImageButton mDeleteButton;
    private HomeworkListFragment mHomeworkListFragment;
    private String mCurrentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_list);
        mHomeworkButton = (ImageButton)findViewById(R.id.add_homework);
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

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeworkContent.ITEMS.remove(HomeworkContent.ITEM_MAP.get(mCurrentID));
                HomeworkContent.ITEM_MAP.remove(mCurrentID);
                mHomeworkListFragment.getAdapter().notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemSelected(String id) {
        mCurrentID = id;

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
