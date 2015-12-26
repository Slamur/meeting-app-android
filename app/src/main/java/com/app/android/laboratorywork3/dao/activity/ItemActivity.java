package com.app.android.laboratorywork3.dao.activity;

import android.app.Activity;
import android.os.Bundle;

import com.app.android.laboratorywork3.dao.AndroidListDao;
import com.app.android.laboratorywork3.dao.AndroidListDaoItemProvider;

import java.util.ArrayList;
import java.util.List;

import model.dao.Item;

public abstract class ItemActivity<ItemType extends Item, ItemDaoType extends AndroidListDao<ItemType>> extends Activity {

    private List<ItemActivityListener<ItemType>> itemActivityListeners;

    protected ItemDaoType dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.itemActivityListeners = new ArrayList<>();
    }

    protected abstract ItemDaoType createDao(AndroidListDaoItemProvider<ItemType> itemProvider);

    protected void setItemProvider(AndroidListDaoItemProvider<ItemType> itemProvider) {
        removeListener(dao);
        this.dao = createDao(itemProvider);
        addListener(dao);
    }

    protected void addListener(ItemActivityListener<ItemType> listener) {
        itemActivityListeners.add(listener);
    }

    protected void removeListener(ItemActivityListener<ItemType> listener) {
        itemActivityListeners.remove(listener);
    }

    protected void notifyListeners() {
        for (ItemActivityListener<ItemType> listener : itemActivityListeners) {
            listener.onSaveItems();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        notifyListeners();
    }

    @Override
    protected void onPause() {
        notifyListeners();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        notifyListeners();
        super.onDestroy();
    }
}
