package com.app.android.laboratorywork3.meeting.dao.rest;

import android.content.Context;

import com.app.android.laboratorywork3.dao.rest.AndroidItemRestProvider;

import model.Participant;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ParticipantActivityProvider extends AndroidItemRestProvider<Participant> {

    protected ParticipantActivityProvider(Context context) {
        super(context);
    }

    @Override
    protected String getItemUrlPath() {
        return "participants/";
    }

    public void loadParticipant(String login, String password) {
        final ParticipantRestApi participantRestApi = createRestApi(ParticipantRestApi.class);
        participantRestApi.getParticipant(login, password, new Callback<Participant>() {

            @Override
            public void success(Participant participant, Response response) {
                notifyItemLoaded(participant);
            }

            @Override
            public void failure(RetrofitError error) {
                showToast(error.getMessage());
            }
        });
    }
}
