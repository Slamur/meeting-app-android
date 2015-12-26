package com.app.android.laboratorywork3.dao.rest;

import java.util.List;

import model.dao.Item;

public interface AndroidItemRestListener<ItemType extends Item> {

    void onItemLoaded(ItemType item);

    void onItemsLoaded(List<ItemType> items);
}
