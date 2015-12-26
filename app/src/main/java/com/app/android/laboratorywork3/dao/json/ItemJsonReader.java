package com.app.android.laboratorywork3.dao.json;

import android.util.JsonReader;

import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import model.dao.Item;

public abstract class ItemJsonReader<ItemType extends Item>
implements Closeable {

    protected JsonReader jsonReader;

    protected ItemJsonReader(Reader in) {
        this.jsonReader = new JsonReader(in);
    }

    protected abstract ItemType getObjectFields() throws IOException;

    public ItemType readItem() throws IOException {
        jsonReader.beginObject();

        ItemType item = getObjectFields();

        jsonReader.endObject();

        return item;
    }

    public List<ItemType> readItems() throws IOException {
        List<ItemType> items = new ArrayList<>();

        jsonReader.beginArray();

        while (jsonReader.hasNext()) {
            ItemType item = readItem();
            if (null != item) {
                items.add(item);
            }
        }

        jsonReader.endArray();

        return items;
    }

    public void close() throws IOException {
        jsonReader.close();
    }
}
