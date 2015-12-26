package com.app.android.laboratorywork3.meeting.dao.rest;

import com.app.android.laboratorywork3.dao.rest.ItemRestApi;

import java.util.List;

import model.Meeting;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface MeetingRestApi extends ItemRestApi<Meeting> {

    @GET("/get/filter/date/{startDate}/{endDate}")
    void getMeetingsByDate(@Path("startDate") long startDate, @Path("endDate") long endDate, Callback<List<Meeting>> response);
}
