package com.aniket.overthecloud;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aniket.overthecloud.data.model.Movie;
import com.aniket.overthecloud.data.model.RowModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView verticalRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verticalRecyclerView = findViewById(R.id.verticalRecyclerView);

        // Set vertical layout manager
        verticalRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );

        // Create dummy movie list
        List<Movie> movieList1 = new ArrayList<>();
        movieList1.add(new Movie("Movie 1"));
        movieList1.add(new Movie("Movie 2"));
        movieList1.add(new Movie("Movie 3"));
        movieList1.add(new Movie("Movie 4"));

        List<Movie> movieList2 = new ArrayList<>();
        movieList2.add(new Movie("Movie A"));
        movieList2.add(new Movie("Movie B"));
        movieList2.add(new Movie("Movie C"));
        movieList2.add(new Movie("Movie D"));
        movieList2.add(new Movie("Movie e"));
        movieList2.add(new Movie("Movie f"));

        // Create rows
        List<RowModel> rowList = new ArrayList<>();
        rowList.add(new RowModel("Trending", movieList1));
        rowList.add(new RowModel("Popular", movieList2));
        rowList.add(new RowModel("Top Rated", movieList1));
        rowList.add(new RowModel("New Releases", movieList2));
        rowList.add(new RowModel("Action", movieList1));
        rowList.add(new RowModel("Comedy", movieList2));
        rowList.add(new RowModel("Drama", movieList1));


        // Set adapter
        RowAdapter rowAdapter = new RowAdapter(this, rowList);
        verticalRecyclerView.setAdapter(rowAdapter);
    }
}
