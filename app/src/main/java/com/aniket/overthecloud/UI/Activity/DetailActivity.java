package com.aniket.overthecloud.UI.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.aniket.overthecloud.R;
import com.aniket.overthecloud.data.model.RowItem;
import com.aniket.overthecloud.databinding.ActivityDetailBinding;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        handleIntentData();
    }

    private void handleIntentData() {
        String json = getIntent().getStringExtra("ITEM_JSON");
        if (json != null && !json.isEmpty()) {
            RowItem movie = new Gson().fromJson(json, RowItem.class);
            displayMovieDetails(movie);
        } else {
            finish();
        }
    }

    private void displayMovieDetails(RowItem movie) {
        binding.tvDetailTitle.setText(movie.getTitle());
        binding.tvDetailSynopsis.setText(movie.getSynopsis());
        binding.tvDetailMeta.setText(movie.getFormattedMeta(this));

        // Display Credits using HTML
        binding.tvDetailCredits.setText(Html.fromHtml(formatCredits(movie), Html.FROM_HTML_MODE_COMPACT));

        // Load Image using the Model's logic
        Glide.with(this)
                .load(movie.getBestImageUrl())
                .placeholder(new ColorDrawable(Color.parseColor("#1A1A1A")))
                .centerCrop()
                .into(binding.imgDetailBackdrop);

        binding.btnPlay.setOnClickListener(v ->
                Toast.makeText(this, "Playing: " + movie.getTitle(), Toast.LENGTH_SHORT).show()
        );
    }

    private String formatCredits(RowItem movie) {
        StringBuilder sb = new StringBuilder();
        if (movie.getGenre() != null) sb.append(getString(R.string.credits_genres, String.join(", ", movie.getGenre()))).append("<br/>");
        if (movie.getDirector() != null) sb.append(getString(R.string.credits_director, String.join(", ", movie.getDirector()))).append("<br/>");
        if (movie.getCast() != null) sb.append(getString(R.string.credits_cast, String.join(", ", movie.getCast()))).append("<br/>");
        return sb.toString();
    }
}