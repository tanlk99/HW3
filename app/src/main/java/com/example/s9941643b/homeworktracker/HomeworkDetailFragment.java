package com.example.s9941643b.homeworktracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * A fragment representing a single Homework detail screen.
 * This fragment is either contained in a {@link HomeworkListActivity}
 * in two-pane mode (on tablets) or a {@link HomeworkDetailActivity}
 * on handsets.
 */
public class HomeworkDetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "homework_id";
    private HomeworkContent.Homework mItem;


    public HomeworkDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = HomeworkContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_homework_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((EditText)rootView.findViewById(R.id.homework_detail_name)).setText(mItem.mName);
            ((EditText)rootView.findViewById(R.id.homework_detail_subject)).setText(mItem.mSubject);
        }

        return rootView;
    }
}
