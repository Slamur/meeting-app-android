package com.app.android.laboratorywork3.dao;

import android.content.Context;

import com.app.android.laboratorywork3.dao.activity.ItemActivityListener;
import com.app.android.laboratorywork3.dao.rest.AndroidItemRestListener;
import com.app.android.laboratorywork3.dao.rest.AndroidItemRestProvider;

import java.util.List;

import model.dao.Item;
import model.dao.ListDao;

public abstract class AndroidListDao<ItemType extends Item>
    extends ListDao<ItemType>
        implements ItemActivityListener<ItemType>, AndroidItemRestListener<ItemType> {

    protected AndroidListDao(AndroidListDaoItemProvider<ItemType> itemProvider) {
        super(itemProvider);
        if (itemProvider instanceof AndroidItemRestProvider) {
            ((AndroidItemRestProvider) itemProvider).addListener(this);
        }
    }

    @Override
    public void onSaveItems() {
        itemProvider.saveItems(items);
    }

    public AndroidListDaoItemProvider<ItemType> getProvider() {
        return (AndroidListDaoItemProvider<ItemType>)itemProvider;
    }

    public Context getContext() {
        return getProvider().getContext();
    }

    public void refreshItems() {
        onItemsLoaded(itemProvider.loadItems());
    }

    @Override
    public void onItemLoaded(ItemType item) {

    }

    @Override
    public void onItemsLoaded(List<ItemType> newItems) {
        for (int index = 0; index < Math.min(items.size(), newItems.size()); ++index) {
            final ItemType oldItem = items.get(index);
            final ItemType newItem = newItems.get(index);

            if (oldItem != newItem) {
                items.set(index, newItem);
                // TODO notify about changing items
            }
        }

        if (items.size() < newItems.size()) {
            for (int index = items.size(); index < newItems.size(); ++index) {
                addItem(newItems.get(index));
            }
        } else {
            for (int index = items.size() - 1; index >= newItems.size(); ++index) {
                removeItem(index);
            }
        }
    }
}
