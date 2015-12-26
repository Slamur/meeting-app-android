package com.app.android.laboratorywork3.dao.json;

import android.util.JsonWriter;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import model.dao.Item;

public abstract class ItemJsonWriter<ItemType extends Item>
implements Closeable {

    protected JsonWriter jsonWriter;

    protected ItemJsonWriter(Writer out, String itemIndent) {
        this.jsonWriter = new JsonWriter(out);
        jsonWriter.setIndent(itemIndent);
    }

    protected abstract void setObjectFields(ItemType item) throws IOException;

    public void writeItem(ItemType item) throws IOException {
        jsonWriter.beginObject();
        setObjectFields(item);
        jsonWriter.endObject();
    }

    public void writeItems(List<ItemType> items) throws IOException {
        jsonWriter.beginArray();

        for (ItemType item : items) {
            writeItem(item);
        }

        jsonWriter.endArray();
    }

    public void close() throws IOException {
        jsonWriter.close();
    }
}
