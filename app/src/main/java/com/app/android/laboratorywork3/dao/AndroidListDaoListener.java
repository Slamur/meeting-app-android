package com.app.android.laboratorywork3.dao;

import model.dao.Item;
import model.dao.ListDaoListener;

public interface AndroidListDaoListener<ItemType extends Item> extends ListDaoListener<ItemType> {

    void onItemChanged(ItemType item, int index);
}
