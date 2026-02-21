package com.aniket.overthecloud.utils;

import android.content.Context;
import java.io.InputStream;
import java.io.IOException;

public class JsonUtils {

    public static String loadJSONFromAsset(Context context) {

        String json = null;

        try {
            InputStream is = context.getAssets().open("thunder_cats.json");

            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }
}
