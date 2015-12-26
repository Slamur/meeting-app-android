package com.app.android.laboratorywork3.meeting.dao;

import com.app.android.laboratorywork3.dao.AndroidListDao;
import com.app.android.laboratorywork3.dao.AndroidListDaoItemProvider;
import com.app.android.laboratorywork3.meeting.dao.rest.MeetingActivityProvider;

import java.util.Date;

import model.Meeting;
import model.MeetingPriority;

public class MeetingActivityDao extends AndroidListDao<Meeting> {

    public MeetingActivityDao(AndroidListDaoItemProvider<Meeting> itemProvider) {
        super(itemProvider);
    }

    @Override
    public Class<Meeting> getItemClass() {
        return Meeting.class;
    }

    public Meeting addMeeting(String title, Date startDate, Date endDate, int priority) {
        Meeting meeting = new Meeting(getNewInstanceId(), title, startDate, endDate, MeetingPriority.values()[priority]);
        addItem(meeting);
        return meeting;
    }

    @Override
    public void refreshItems() {
        if (itemProvider instanceof MeetingActivityProvider) {
            ((MeetingActivityProvider) itemProvider).loadTodayMeetings();
        } else {
            super.refreshItems();
        }
    }
}
