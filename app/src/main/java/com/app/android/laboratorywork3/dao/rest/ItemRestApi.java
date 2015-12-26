package com.app.android.laboratorywork3.dao.rest;

import java.util.List;

import model.dao.Item;
import retrofit.Callback;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface ItemRestApi<ItemType extends Item> {

    @GET("/get/items")
    void getItems(Callback<List<ItemType>> response);

    @GET("/get/{index}")
    void getItem(@Path("index") int index, Callback<ItemType> response);

    @PUT("put/item")
    void addItem(ItemType item, Callback<String> response);

    @DELETE("/delete/{index}")
    void deleteItem(@Path("index") int index, Callback<String> response);
}
