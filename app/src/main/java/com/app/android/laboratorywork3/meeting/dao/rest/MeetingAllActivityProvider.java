package com.app.android.laboratorywork3.meeting.dao.rest;

import android.content.Context;

import com.app.android.laboratorywork3.R;
import com.app.android.laboratorywork3.dao.AndroidListDao;
import com.app.android.laboratorywork3.dao.rest.AndroidItemRestProvider;
import com.app.android.laboratorywork3.dao.rest.ItemRestApi;
import com.app.android.laboratorywork3.meeting.dao.json.MeetingJsonIOUtils;
import com.app.android.laboratorywork3.meeting.dao.json.MeetingJsonWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import model.Meeting;
import model.dao.ListDao;
import model.dao.ListDaoListener;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MeetingAllActivityProvider
        extends AndroidItemRestProvider<Meeting>
        implements ListDaoListener<Meeting> {

    protected AndroidListDao<Meeting> meetingDao;

    public MeetingAllActivityProvider(Context context) {
        super(context);
    }

    @Override
    protected String getItemUrlPath() {
        return "meetings/";
    }

    @Override
    public void saveItems(List<Meeting> meetings) {
        final String meetingFileName = context.getResources().getString(R.string.meeting_file_name);

        try (OutputStream meetingOut = context.openFileOutput(meetingFileName, Context.MODE_PRIVATE)) {
            try (MeetingJsonWriter meetingJSONWriter = MeetingJsonIOUtils.openEventWriter(meetingOut)) {
                meetingJSONWriter.writeItems(meetings);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Meeting> loadItems() {
        final ItemRestApi<Meeting> meetingItemRestApi = createRestAdapter().create(ItemRestApi.class);
        meetingItemRestApi.getItems(new Callback<List<Meeting>>() {
            @Override
            public void success(List<Meeting> meetings, Response response) {
                notifyItemsLoaded(meetings);
            }

            @Override
            public void failure(RetrofitError error) {
                showToast(error.getMessage());
            }
        });

        return new ArrayList<>();
    }

    @Override
    public void notifyItemAdded(Meeting meeting) {
        final ItemRestApi<Meeting> meetingRestApi = createRestApi(ItemRestApi.class);
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
    public void notifyItemInserted(Meeting meeting, int index) {

    }

    @Override
    public void notifyItemRemoved(int index) {
        onItemRemoved(index);
    }

    @Override
    public void setProducer(ListDao<Meeting> meetingDao) {
        this.meetingDao = (AndroidListDao<Meeting>)meetingDao;
    }

    @Override
    public ListDao<Meeting> getProducer() {
        return null;
    }
}
