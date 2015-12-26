package com.app.android.laboratorywork3.meeting.dao.rest;

import android.content.Context;

import com.app.android.laboratorywork3.dao.rest.AndroidItemRestProvider;

import model.Meeting;
import model.MeetingWithSummary;
import model.dao.ListDao;
import model.dao.ListDaoListener;

public class MeetingWithSummaryRemoveActivityProvider
        extends AndroidItemRestProvider<MeetingWithSummary>
        implements ListDaoListener<Meeting> {

    public MeetingWithSummaryRemoveActivityProvider(Context context) {
        super(context);
    }

    @Override
    protected String getItemUrlPath() {
        return "summaries/";
    }


    @Override
    public void notifyItemAdded(Meeting meeting) {

    }

    @Override
    public void notifyItemInserted(Meeting meeting, int index) {

    }

    @Override
    public void notifyItemRemoved(int index) {
        onItemRemoved(index);
    }

    @Override
    public void setProducer(ListDao<Meeting> meetingDao) {

    }

    @Override
    public ListDao<Meeting> getProducer() {
        return null;
    }
}
