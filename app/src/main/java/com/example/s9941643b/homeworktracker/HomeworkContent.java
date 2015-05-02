package com.example.s9941643b.homeworktracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by S9941643B on 4/7/2015.
 */

public class HomeworkContent {
    public static List<Homework> ITEMS = new ArrayList<Homework>();
    public static Map<String, Homework> ITEM_MAP = new HashMap<String, Homework>();
    public static int mCurrentID = 1;

    static {
        addItem(new Homework("Weed Agriculture", "Herbology", new GregorianCalendar(), new GregorianCalendar()));
        addItem(new Homework("Eating Food", "Biology", new GregorianCalendar(), new GregorianCalendar()));
        addItem(new Homework("Sleeping", "Neuroscience", new GregorianCalendar(), new GregorianCalendar()));
        addItem(new Homework("Homework", "Procrastination", new GregorianCalendar(), new GregorianCalendar()));
        addItem(new Homework("Binge Watching", "Entertainment", new GregorianCalendar(), new GregorianCalendar()));
        addItem(new Homework("More Sleeping", "Further Neuroscience", new GregorianCalendar(), new GregorianCalendar()));
    }

    public static void addItem(Homework item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.mID, item);
    }

    public static class HomeworkComparator implements Comparator<Homework> {
        public int compare(Homework hw1, Homework hw2) {
            return hw1.mDateDue.compareTo(hw2.mDateDue);
        }
    }

    public static void sortHomework() {
        Collections.sort(ITEMS, new HomeworkComparator());
    }

    public static class Homework {
        public String mID, mName, mSubject;
        public GregorianCalendar mDateDue, mDateRemind;
        public boolean mHomeworkDone;

        public Homework(String name, String subject, GregorianCalendar dateDue, GregorianCalendar dateRemind) {
            mID = Integer.toString(mCurrentID);
            mCurrentID++;

            mName = name;
            mSubject = subject;
            mDateDue = dateDue;
            mDateRemind = dateRemind;
            mHomeworkDone = false;
        }
    }
}
