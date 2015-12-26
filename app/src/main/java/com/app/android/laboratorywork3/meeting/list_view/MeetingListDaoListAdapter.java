package com.app.android.laboratorywork3.meeting.list_view;

import android.view.View;
import android.view.ViewGroup;

import com.app.android.laboratorywork3.R;
import com.app.android.laboratorywork3.dao.AndroidUtils;
import com.app.android.laboratorywork3.dao.list_view.ListDaoListAdapter;
import com.app.android.laboratorywork3.meeting.activity.MeetingListActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import model.Meeting;

public class MeetingListDaoListAdapter extends ListDaoListAdapter<Meeting>{

    private final MeetingListActivity activity;

    public MeetingListDaoListAdapter(MeetingListActivity activity) {
        this.activity = activity;
    }

    public static String getLabeledText(String label, Object object) {
        return label + ": " + object.toString();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (null == view) {
            view = layoutInflater.inflate(R.layout.meeting_list_item, parent, false);
        }

        Meeting meeting = producer.getItem(position);

        AndroidUtils.setText(view, R.id.meeting_list_item_id, getLabeledText("Id", meeting.getId()));
        AndroidUtils.setText(view, R.id.meeting_list_item_title, getLabeledText("Title", meeting.getTitle()));

        AndroidUtils.setText(view, R.id.meeting_list_item_priority, getLabeledText("Priority", meeting.getPriority().name()));

        DateFormat sdf = new SimpleDateFormat(view.getContext().getString(R.string.date_format));
        AndroidUtils.setText(view, R.id.meeting_list_item_startDate, getLabeledText("Starts at", sdf.format(meeting.getStartDate())));
        AndroidUtils.setText(view, R.id.meeting_list_item_endDate, getLabeledText("Ends at", sdf.format(meeting.getEndDate())));

        // TODO onClick of button

        return view;
    }
}
