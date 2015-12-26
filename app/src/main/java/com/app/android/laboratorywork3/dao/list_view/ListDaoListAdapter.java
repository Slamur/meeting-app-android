package com.app.android.laboratorywork3.dao.list_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import com.app.android.laboratorywork3.dao.AndroidListDao;
import com.app.android.laboratorywork3.dao.AndroidListDaoListener;

import model.dao.Item;
import model.dao.ListDao;

public abstract class ListDaoListAdapter
        <ItemType extends Item>
        extends BaseAdapter
        implements AndroidListDaoListener<ItemType> {

    protected AndroidListDao<ItemType> producer;
    protected LayoutInflater layoutInflater;

    @Override
    public void setProducer(ListDao<ItemType> producer) {
        this.producer = (AndroidListDao<ItemType>)producer;
        this.layoutInflater = (LayoutInflater) this.producer.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ListDao<ItemType> getProducer() {
        return producer;
    }

    @Override
    public int getCount() {
        return producer.getItemCount();
    }

    @Override
    public ItemType getItem(int position) {
        return producer.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return producer.getItem(position).getId();
    }


    @Override
    public void notifyItemAdded(ItemType item) {
        notifyDataSetChanged();
    }

    @Override
    public void notifyItemInserted(ItemType item, int index) {
        notifyDataSetChanged();
    }

    @Override
    public void notifyItemRemoved(int index) {
        notifyDataSetChanged();
    }

    @Override
    public void onItemChanged(ItemType item, int index) {
        notifyDataSetChanged();
    }
}
