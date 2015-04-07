package com.example.s9941643b.homeworktracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

/**
 * Created by S9941643B on 4/7/2015.
 */

public class HomeworkContent {
    public static List<Homework> ITEMS = new ArrayList<Homework>();
    public static Map<String, Homework> ITEM_MAP = new HashMap<String, Homework>();

    public static class Homework {
        String mName, mSubject;
        Date mDateDue, mDateRemind;
        boolean mHomeworkDone;

        public Homework(String name, String subject, Date dateDue, Date dateRemind) {
            mName = name;
            mSubject = subject;
            mDateDue = dateDue;
            mDateRemind = dateRemind;
            mHomeworkDone = false;
        }
    }
}
