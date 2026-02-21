package com.aniket.overthecloud.UI;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aniket.overthecloud.R;
import com.aniket.overthecloud.Adapter.RowAdapter;
import com.aniket.overthecloud.data.model.MainResponse;
import com.aniket.overthecloud.data.model.Row;
import com.aniket.overthecloud.utils.JsonUtils;
import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvRows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // STEP 1: Initialize RecyclerView
        rvRows = findViewById(R.id.rvRows);

        // STEP 2: Set Vertical Layout
        rvRows.setLayoutManager(new LinearLayoutManager(this));

        // STEP 3: Parse JSON & set adapter
        parseJsonAndSetupUI();
    }

    private void parseJsonAndSetupUI() {

        // STEP 4: Load JSON from assets
        String jsonString = JsonUtils.loadJSONFromAsset(this);

        if (jsonString == null) {
            Log.e(TAG, "JSON is null");
            return;
        }

        // STEP 5: Convert JSON → Java Object
        Gson gson = new Gson();
        MainResponse response = gson.fromJson(jsonString, MainResponse.class);

        if (response != null && response.getRows() != null) {

            List<Row> rows = response.getRows();

            Log.d(TAG, "Total Rows: " + rows.size());

            // STEP 6: Set Adapter
            RowAdapter adapter = new RowAdapter(this, rows);
            rvRows.setAdapter(adapter);

        } else {
            Log.e(TAG, "Response or rows is null");
        }
    }
}

//verticalRecyclerView = findViewById(R.id.verticalRecyclerView);
//
//// Set vertical layout manager
//        verticalRecyclerView.setLayoutManager(
//                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        );
//List<Movie> movieList1 = new ArrayList<>();
//        movieList1.add(new Movie("Movie 1"));
//        movieList1.add(new Movie("Movie 2"));
//        movieList1.add(new Movie("Movie 3"));
//        movieList1.add(new Movie("Movie 4"));
//
//List<Movie> movieList2 = new ArrayList<>();
//        movieList2.add(new Movie("Movie A"));
//        movieList2.add(new Movie("Movie B"));
//        movieList2.add(new Movie("Movie C"));
//        movieList2.add(new Movie("Movie D"));
//        movieList2.add(new Movie("Movie e"));
//        movieList2.add(new Movie("Movie f"));
//
//// Create rows
//List<RowModel> rowList = new ArrayList<>();
//        rowList.add(new RowModel("Trending", movieList1));
//        rowList.add(new RowModel("Popular", movieList2));
//        rowList.add(new RowModel("Top Rated", movieList1));
//        rowList.add(new RowModel("New Releases", movieList2));
//        rowList.add(new RowModel("Action", movieList1));
//        rowList.add(new RowModel("Comedy", movieList2));
//        rowList.add(new RowModel("Drama", movieList1));
//
//
//// Set adapter
//RowAdapter rowAdapter = new RowAdapter(this, rowList);
//        verticalRecyclerView.setAdapter(rowAdapter);
