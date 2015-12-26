package com.app.android.laboratorywork3.dao;

import android.content.Context;

import model.dao.Item;
import model.dao.ListDaoItemProvider;

public abstract class AndroidListDaoItemProvider<ItemType extends Item>
        implements ListDaoItemProvider<ItemType> {

    protected final Context context;

    protected AndroidListDaoItemProvider(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
}
