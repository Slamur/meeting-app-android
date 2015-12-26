package com.app.android.laboratorywork3.meeting.dao.rest;

import android.content.Context;

import com.app.android.laboratorywork3.dao.rest.AndroidItemRestProvider;
import com.app.android.laboratorywork3.dao.rest.ItemRestApi;

import model.MeetingWithSummary;
import model.dao.ListDao;
import model.dao.ListDaoListener;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MeetingWithSummaryActivityProvider
        extends AndroidItemRestProvider<MeetingWithSummary>
        implements ListDaoListener<MeetingWithSummary> {

    public MeetingWithSummaryActivityProvider(Context context) {
        super(context);
    }

    @Override
    protected String getItemUrlPath() {
        return "summaries/";
    }

    public void loadMeetingWithSubstring(String substring) {
        final MeetingWithSummaryRestApi meetingWithSummaryRestApi = createRestApi(MeetingWithSummaryRestApi.class);
        meetingWithSummaryRestApi.getMeetingBySubstring(substring, new Callback<MeetingWithSummary>() {

            @Override
            public void success(MeetingWithSummary meetingWithSummary, Response response) {
                notifyItemLoaded(meetingWithSummary);
            }

            @Override
            public void failure(RetrofitError error) {
                showToast(error.getMessage());
            }
        });
    }

    @Override
    public void notifyItemAdded(MeetingWithSummary meeting) {
        final ItemRestApi<MeetingWithSummary> meetingRestApi = createRestApi(ItemRestApi.class);
        meetingRestApi.addItem(meeting, new Callback<String>() {

            @Override
            public void success(String message, Response response) {
                showToast(message);
            }

            @Override
            public void failure(RetrofitError error) {
                showToast(error.getMessage());
            }
        });
    }

    @Override
    public void notifyItemInserted(MeetingWithSummary meeting, int index) {

    }

    @Override
    public void notifyItemRemoved(int index) {
        onItemRemoved(index);
    }

    @Override
    public void setProducer(ListDao<MeetingWithSummary> meetingDao) {

    }

    @Override
    public ListDao<MeetingWithSummary> getProducer() {
        return null;
    }
}
