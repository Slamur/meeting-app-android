package com.app.android.laboratorywork3.meeting.dao.json;

import com.app.android.laboratorywork3.dao.json.ItemJsonReader;

import java.io.IOException;
import java.io.Reader;

import model.Meeting;

public class MeetingJsonReader
extends ItemJsonReader<Meeting> {

    public MeetingJsonReader(Reader in) {
        super(in);
    }

    @Override
    protected Meeting getObjectFields() throws IOException {
        Long id = null;

        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if (MeetingJsonIOUtils.ID_FIELD.equals(name)) {
                id = jsonReader.nextLong();
            } else {
                jsonReader.skipValue();
            }
        }

        if (null == id) return null;

        return new Meeting(id, null, null, null, null, null);
    }
}
