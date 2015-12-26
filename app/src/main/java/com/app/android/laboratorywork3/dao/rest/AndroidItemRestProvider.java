package com.app.android.laboratorywork3.dao.rest;

import android.content.Context;

import com.app.android.laboratorywork3.R;
import com.app.android.laboratorywork3.dao.AndroidListDaoItemProvider;
import com.app.android.laboratorywork3.dao.AndroidUtils;

import java.util.ArrayList;
import java.util.List;

import model.dao.Item;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public abstract class AndroidItemRestProvider <ItemType extends Item> extends AndroidListDaoItemProvider<ItemType> {

    protected final List<AndroidItemRestListener<ItemType>> listeners;

    protected AndroidItemRestProvider(Context context) {
        super(context);
        this.listeners = new ArrayList<>();
    }

    public void addListener(AndroidItemRestListener<ItemType> listener) {
        listeners.add(listener);
    }

    public void removeListener(AndroidItemRestListener<ItemType> listener) {
        listeners.remove(listener);
    }

    protected void notifyItemLoaded(ItemType item) {
        for (AndroidItemRestListener<ItemType> listener : listeners) {
            listener.onItemLoaded(item);
        }
    }

    protected void notifyItemsLoaded(List<ItemType> items) {
        for (AndroidItemRestListener<ItemType> listener : listeners) {
            listener.onItemsLoaded(items);
        }
    }

    protected abstract String getItemUrlPath();

    protected RestAdapter createRestAdapter() {
        //create an adapter for retrofit with base url
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(context.getString(R.string.baseURL) + getItemUrlPath())
                .setRequestInterceptor(new ApiRequestInterceptor(context))
                .setClient(new OkClient())
                .build();

        return restAdapter;
    }

    protected <RestApiType> RestApiType createRestApi(Class<RestApiType> restApiClass) {
        return createRestAdapter().create(restApiClass);
    }

    @Override
    public void saveItems(List<ItemType> list) {

    }

    @Override
    public List<ItemType> loadItems() {
        return new ArrayList<>();
    }

    public void showToast(String message) {
        AndroidUtils.showToast(context, message);
    }

    public void loadItem(int index) {
        final ItemRestApi<ItemType> itemRestApi = createRestApi(ItemRestApi.class);
        itemRestApi.getItem(index, new Callback<ItemType>() {

            @Override
            public void success(ItemType item, Response response) {
                notifyItemLoaded(item);
            }

            @Override
            public void failure(RetrofitError error) {
                showToast(error.getMessage());
            }

        });
    }

    protected void onItemRemoved(int index) {
        final ItemRestApi meetingRestApi = createRestApi(ItemRestApi.class);
        meetingRestApi.deleteItem(index, new Callback<String>() {

            @Override
            public void success(String message, Response response) {
                showToast(message);
            }

            @Override
            public void failure(RetrofitError error) {
                showToast(error.getMessage());
            }
        });
    }
}
