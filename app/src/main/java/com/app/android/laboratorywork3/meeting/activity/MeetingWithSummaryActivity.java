package com.app.android.laboratorywork3.meeting.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.app.android.laboratorywork3.R;
import com.app.android.laboratorywork3.dao.AndroidListDaoItemProvider;
import com.app.android.laboratorywork3.dao.AndroidUtils;
import com.app.android.laboratorywork3.dao.activity.ItemActivity;
import com.app.android.laboratorywork3.dao.rest.AndroidItemRestListener;
import com.app.android.laboratorywork3.meeting.dao.MeetingActivityDao;
import com.app.android.laboratorywork3.meeting.dao.MeetingWithSummaryActivityDao;
import com.app.android.laboratorywork3.meeting.dao.rest.MeetingAllActivityProvider;
import com.app.android.laboratorywork3.meeting.dao.rest.MeetingWithSummaryActivityProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.Meeting;
import model.MeetingPriority;
import model.MeetingWithSummary;

public class MeetingWithSummaryActivity extends ItemActivity<MeetingWithSummary, MeetingWithSummaryActivityDao>
implements AndroidItemRestListener<MeetingWithSummary> {

    public static final String ACTION_TYPE_EXTRA = "Action type extra";
    public static final int
            NO_ACTION = -1,
            ADD_MEETING_ACTION = 0,
            GET_SUMMARY = 1,
            SEARCH_SUBSTRING = 2;

    private static final int START_DATE_INDEX = 0, END_DATE_INDEX = 1;

    private static final String NO_SUCH_MEETINGS_MESSAGE = "No meetings for this pattern found";

    @Override
    protected MeetingWithSummaryActivityDao createDao(AndroidListDaoItemProvider<MeetingWithSummary> itemProvider) {
        return new MeetingWithSummaryActivityDao(itemProvider);
    }

    private MeetingActivityDao meetingDao;

    private String mTitle, mSummary;

    private DateFormat sdf;
    private Date[] mDates;
    private int[] mDateLabelIds = { R.id.meeting_start_date_label, R.id.meeting_end_date_label };
    private int[] mDateLabelPrefixIds = { R.string.meeting_start_date_label, R.string.meeting_end_date_label };

    private int mPriority;
    private Spinner mPrioritySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_with_summary);

        MeetingWithSummaryActivityProvider meetingWithSummaryActivityProvider = new MeetingWithSummaryActivityProvider(this);
        setItemProvider(meetingWithSummaryActivityProvider);

        meetingWithSummaryActivityProvider.addListener(this);

        this.meetingDao = new MeetingActivityDao(new MeetingAllActivityProvider(this));

        initDates();
        initSpinner();

        switch (getActionType()) {
            case ADD_MEETING_ACTION:
                break;
            case GET_SUMMARY:
            case SEARCH_SUBSTRING:
                disableInputs();
                break;
            default:
                finish();
        }

    }

    private int getActionType() {
        Intent intent = getIntent();
        return intent.getIntExtra(ACTION_TYPE_EXTRA, NO_ACTION);
    }

    private void disableInputs() {
        findViewById(R.id.meeting_title_edit).setEnabled(false);
        findViewById(R.id.meeting_summary_edit).setEnabled(false);
        findViewById(R.id.meeting_start_date_button).setEnabled(false);
        findViewById(R.id.meeting_end_date_button).setEnabled(false);
        findViewById(R.id.meeting_priority_spinner).setEnabled(false);
    }

    private void showDate(int dateIndex) {
        AndroidUtils.setText(
                this,
                mDateLabelIds[dateIndex],
                getString(mDateLabelPrefixIds[dateIndex]) + sdf.format(mDates[dateIndex])
        );
    }

    private void setDate(int dateIndex, Date date) {
        mDates[dateIndex] = date;
        showDate(dateIndex);
    }

    private void initDates() {
        this.sdf = new SimpleDateFormat(getString(R.string.date_format));

        this.mDates = new Date[2];
        setDate(START_DATE_INDEX, Calendar.getInstance().getTime());
        setDate(END_DATE_INDEX, new Date(this.mDates[START_DATE_INDEX].getTime() + 24 * 60 * 60 * 1000));
    }

    private void initSpinner() {
        this.mPrioritySpinner = (Spinner) findViewById(R.id.meeting_priority_spinner);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.meeting_priorities, android.R.layout.simple_spinner_item);

        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        mPrioritySpinner.setAdapter(adapter);

        mPrioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPriority = position;
                if (mPriority >= MeetingPriority.values().length) {
                    mPriority = MeetingPriority.values().length - 1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mPriority = MeetingPriority.values().length - 1;
            }
        });
    }

    public void chooseStartDate(View v){
        createDateDialog("Choose meeting start time", START_DATE_INDEX);
    }

    public void chooseEndDate(View v){
        createDateDialog("Choose meeting start end", END_DATE_INDEX);
    }

    private void createDateDialog(String title, final int dateIndex) {

        RelativeLayout dialogView = (RelativeLayout) getLayoutInflater()
                .inflate(R.layout.datetime_picker_dialog, null);

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setView(dialogView)
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dialog thisDialog = Dialog.class.cast(dialog);
                        DatePicker date = (DatePicker) thisDialog.findViewById(R.id.datePicker);
                        TimePicker time = (TimePicker) thisDialog.findViewById(R.id.timePicker);

                        Calendar totalDate = Calendar.getInstance();
                        totalDate.set(
                                date.getYear(), date.getMonth(), date.getDayOfMonth(),
                                time.getHour(), time.getMinute()
                        );

                        setDate(dateIndex, totalDate.getTime());
                    }
                })
                .create();

        alertDialog.show();
    }

    public void AddMeeting(View view){
        mTitle = ((EditText) findViewById(R.id.meeting_title_edit)).getText().toString();
        mSummary = ((EditText) findViewById(R.id.meeting_summary_edit)).getText().toString();

        Meeting meeting = meetingDao.addMeeting(mTitle, mDates[START_DATE_INDEX], mDates[END_DATE_INDEX], mPriority);
        dao.addMeetingWithSummary(meeting, mSummary);

        Intent intent = new Intent();
        setResult(RESULT_OK, intent);

        finish();
    }

    @Override
    public void onItemLoaded(MeetingWithSummary meetingWithSummary) {
        if (null == meetingWithSummary) {
            AndroidUtils.showToast(getApplicationContext(), NO_SUCH_MEETINGS_MESSAGE);
        } else {
            AndroidUtils.setText(this, R.id.meeting_title_label, mTitle = meetingWithSummary.getMeeting().getTitle());
            AndroidUtils.setText(this, R.id.meeting_summary_label, mSummary = meetingWithSummary.getSummary());

            setDate(START_DATE_INDEX, meetingWithSummary.getMeeting().getStartDate());
            setDate(END_DATE_INDEX, meetingWithSummary.getMeeting().getEndDate());

            mPrioritySpinner.setSelection(mPriority = meetingWithSummary.getMeeting().getPriority().ordinal());
        }
    }

    @Override
    public void onItemsLoaded(List<MeetingWithSummary> items) {

    }
}
