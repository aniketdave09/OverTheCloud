package com.aniket.overthecloud.data.repository;

import android.content.Context;

import com.aniket.overthecloud.data.model.MainResponse;
import com.aniket.overthecloud.utils.JsonUtils;
import com.google.gson.Gson;

public class MovieRepository {
    private Context context;

    public MovieRepository(Context context) {
        this.context = context;
    }

    public MainResponse getMoviesData() {
        String jsonString = JsonUtils.loadJSONFromAsset(context);
        if (jsonString != null) {
            return new Gson().fromJson(jsonString, MainResponse.class);
        }
        return null;
    }
}