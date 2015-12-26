package com.app.android.laboratorywork3.meeting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.app.android.laboratorywork3.R;
import com.app.android.laboratorywork3.dao.AndroidListDaoItemProvider;
import com.app.android.laboratorywork3.dao.activity.ItemActivity;
import com.app.android.laboratorywork3.meeting.dao.MeetingActivityDao;
import com.app.android.laboratorywork3.meeting.dao.rest.MeetingActivityProvider;
import com.app.android.laboratorywork3.meeting.dao.rest.MeetingWithSummaryRemoveActivityProvider;
import com.app.android.laboratorywork3.meeting.list_view.MeetingListDaoListAdapter;

import model.Meeting;

public class MeetingListActivity extends ItemActivity<Meeting, MeetingActivityDao> {

    public static final String MEETING_INDEX_EXTRA = "Meeting index extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_list);

        MeetingActivityProvider meetingProvider = new MeetingActivityProvider(this);
        setItemProvider(meetingProvider);

        dao.addListener(meetingProvider);
        dao.addListener(
                new MeetingWithSummaryRemoveActivityProvider(this)
        );

        initAdapter();

        dao.refreshItems();
    }

    @Override
    protected MeetingActivityDao createDao(AndroidListDaoItemProvider<Meeting> meetingProvider) {
        return new MeetingActivityDao(meetingProvider);
    }

    public void initAdapter() {
        MeetingListDaoListAdapter adapter = new MeetingListDaoListAdapter(this);

        ListView meetingsListView = (ListView) findViewById(R.id.meetings_list_view);
        meetingsListView.setAdapter(adapter);

        dao.addListener(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_meeting_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            throw new UnsupportedOperationException();
        }

        return super.onOptionsItemSelected(item);
    }

    private Intent getMeetingWithSummaryActivityIntent(int actionType) {
        Intent addMeetingActivityIntent = new Intent(
                getBaseContext(),
                MeetingWithSummaryActivity.class);

        addMeetingActivityIntent.putExtra(MeetingWithSummaryActivity.ACTION_TYPE_EXTRA, actionType);

        return addMeetingActivityIntent;
    }

    public void getMeetingWithSummary(int index) {
        Intent addMeetingActivityIntent = getMeetingWithSummaryActivityIntent(MeetingWithSummaryActivity.GET_SUMMARY);
        addMeetingActivityIntent.putExtra(MEETING_INDEX_EXTRA, index);
        startActivity(addMeetingActivityIntent);
    }

    public void deleteMeeting(int index) { dao.removeItem(index); }

    public void refreshMeetings(View view) {
        dao.refreshItems();
    }

    public void showAddMeetingActivity(View view) {
        Intent addMeetingActivityIntent = getMeetingWithSummaryActivityIntent(MeetingWithSummaryActivity.ADD_MEETING_ACTION);
        startActivity(addMeetingActivityIntent);
    }
}
