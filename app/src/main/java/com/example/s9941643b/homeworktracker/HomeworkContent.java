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

    static {
        addItem(new Homework("Weed Agriculture", "Biology", new Date(1000000000), new Date(1000000000)));
        addItem(new Homework("Eating Food", "Biology", new Date(1000000000), new Date(1000000000)));
        addItem(new Homework("Sleeping", "Neuroscience", new Date(1000000000), new Date(1000000000)));
    }

    private static void addItem(Homework item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.mName, item);
    }

    public static class Homework {
        public String mName, mSubject;
        public Date mDateDue, mDateRemind;
        public boolean mHomeworkDone;

        public Homework(String name, String subject, Date dateDue, Date dateRemind) {
            mName = name;
            mSubject = subject;
            mDateDue = dateDue;
            mDateRemind = dateRemind;
            mHomeworkDone = false;
        }
    }
}
