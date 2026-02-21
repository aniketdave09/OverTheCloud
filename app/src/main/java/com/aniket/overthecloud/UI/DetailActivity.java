package com.aniket.overthecloud.UI;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aniket.overthecloud.R;
import com.aniket.overthecloud.data.model.RowItem;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

public class DetailActivity extends AppCompatActivity {

    private ImageView imgBackdrop;
    private TextView tvTitle, tvMeta, tvSynopsis, tvCredits;
    private Button btnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initViews();
        handleIntentData();
    }

    private void initViews() {
        imgBackdrop = findViewById(R.id.imgDetailBackdrop);
        tvTitle = findViewById(R.id.tvDetailTitle);
        tvMeta = findViewById(R.id.tvDetailMeta);
        tvSynopsis = findViewById(R.id.tvDetailSynopsis);
        tvCredits = findViewById(R.id.tvDetailCredits);
        btnPlay = findViewById(R.id.btnPlay);
    }

    private void handleIntentData() {
        String json = getIntent().getStringExtra("ITEM_JSON");
        if (json == null || json.isEmpty()) {
            finish();
            return;
        }

        RowItem movie = new Gson().fromJson(json, RowItem.class);

        if (movie != null) {
            // 1. Text Data
            tvTitle.setText(movie.getTitle());
            tvSynopsis.setText(movie.getSynopsis());

            // 2. Metadata Bar (Year | Runtime | Rating | Type)
            String metaString = movie.getYear() + "  |  " +
                    movie.getRuntime() + "  |  ⭐ " +
                    movie.getRating() + "  |  " +
                    movie.getType().toUpperCase();
            tvMeta.setText(metaString);

            // 3. Credits Section (Cast, Directors, Genres)
            StringBuilder credits = new StringBuilder();

            if (!movie.getGenre().isEmpty()) {
                credits.append("Genres: ").append(String.join(", ", movie.getGenre())).append("\n");
            }
            if (!movie.getDirector().isEmpty()) {
                credits.append("Director: ").append(String.join(", ", movie.getDirector())).append("\n");
            }
            if (!movie.getCast().isEmpty()) {
                credits.append("Cast: ").append(String.join(", ", movie.getCast())).append("\n");
            }
            if (!movie.getSource().isEmpty()) {
                credits.append("Source: ").append(movie.getSource());
            }

            tvCredits.setText(credits.toString());

            // 4. Image Loading
            // Use background if available, otherwise fallback to poster
            String imageUrl = movie.getBackground().isEmpty() ? movie.getPoster() : movie.getBackground();

            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(new ColorDrawable(Color.parseColor("#1A1A1A")))
                    .error(R.drawable.ic_launcher_background)
                    .centerCrop()
                    .into(imgBackdrop);

            // 5. Action Button
            btnPlay.setOnClickListener(v -> {
                String url = movie.getAlternateUrl().isEmpty() ? movie.getPlaystoreUrl() : movie.getAlternateUrl();
                Toast.makeText(this, "Opening: " + movie.getTitle(), Toast.LENGTH_SHORT).show();
                // Logic to open Video Player would go here
            });
        }
    }
}