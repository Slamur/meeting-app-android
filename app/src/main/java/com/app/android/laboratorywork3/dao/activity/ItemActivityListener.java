package com.app.android.laboratorywork3.dao.activity;

import model.dao.Item;

public interface ItemActivityListener<ItemType extends Item> {

    void onSaveItems();
}
