package com.app.android.laboratorywork3.meeting.dao;

import com.app.android.laboratorywork3.dao.AndroidListDao;
import com.app.android.laboratorywork3.dao.AndroidListDaoItemProvider;

import model.Meeting;
import model.MeetingWithSummary;

public class MeetingWithSummaryActivityDao extends AndroidListDao<MeetingWithSummary> {

    public MeetingWithSummaryActivityDao(AndroidListDaoItemProvider<MeetingWithSummary> itemProvider) {
        super(itemProvider);
    }

    @Override
    public Class<MeetingWithSummary> getItemClass() {
        return MeetingWithSummary.class;
    }

    public MeetingWithSummary addMeetingWithSummary(Meeting meeting, String summary) {
        MeetingWithSummary meetingWithSummary = new MeetingWithSummary(meeting, summary);
        addItem(meetingWithSummary);
        return meetingWithSummary;
    }
}
