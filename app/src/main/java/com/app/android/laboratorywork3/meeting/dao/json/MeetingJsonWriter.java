package com.app.android.laboratorywork3.meeting.dao.json;

import com.app.android.laboratorywork3.dao.json.ItemJsonWriter;

import java.io.IOException;
import java.io.Writer;

import model.Meeting;

public class MeetingJsonWriter
extends ItemJsonWriter<Meeting> {

    MeetingJsonWriter(Writer out) {
        super(out, MeetingJsonIOUtils.MEETING_INDENT);
    }

    protected void setObjectFields(Meeting meeting) throws IOException {
        jsonWriter.name(MeetingJsonIOUtils.ID_FIELD).value(meeting.getId());
    }
}
