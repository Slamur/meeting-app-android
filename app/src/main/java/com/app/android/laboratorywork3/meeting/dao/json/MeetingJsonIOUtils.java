package com.app.android.laboratorywork3.meeting.dao.json;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class MeetingJsonIOUtils {
    public static final String MEETING_ENCODING = "UTF-8";
    public static final String MEETING_INDENT = " ";

    public static final String
            ID_FIELD = "Id field";

    public static MeetingJsonWriter openEventWriter(OutputStream eventOut) throws UnsupportedEncodingException {
        Writer eventWriter = new OutputStreamWriter(eventOut, MEETING_ENCODING);
        return new MeetingJsonWriter(eventWriter);
    }

    public static MeetingJsonReader openEventReader(InputStream eventIn) {
        Reader eventReader = new InputStreamReader(eventIn);
        return new MeetingJsonReader(eventReader);
    }
}
