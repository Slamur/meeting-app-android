package com.app.android.laboratorywork3.meeting.dao.rest;

import com.app.android.laboratorywork3.dao.rest.ItemRestApi;

import model.MeetingWithSummary;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface MeetingWithSummaryRestApi extends ItemRestApi<MeetingWithSummary> {

    @GET("/get/filter/substring/{substring}")
    void getMeetingBySubstring(@Path("substring") String substring, Callback<MeetingWithSummary> response);
}
