package com.app.android.laboratorywork3.meeting.dao.rest;

import com.app.android.laboratorywork3.dao.rest.ItemRestApi;

import model.Participant;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface ParticipantRestApi extends ItemRestApi<Participant> {

    @GET("/get/{login}/{password}")
    void getParticipant(@Path("login") String login, @Path("password") String password, Callback<Participant> response);
}
