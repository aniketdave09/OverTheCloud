package com.aniket.overthecloud.UI.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aniket.overthecloud.UI.Activity.DetailActivity;
import com.aniket.overthecloud.data.model.RowItem;
import com.aniket.overthecloud.databinding.ItemMovieBinding;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

public class RowItemAdapter extends RecyclerView.Adapter<RowItemAdapter.ViewHolder> {

    private final List<RowItem> list;
    private final Context context;
    private final String rowLayout;
    private final String rowType;

    public RowItemAdapter(Context context, List<RowItem> list, String rowLayout, String rowType) {
        this.context = context;
        this.list = list;
        this.rowLayout = rowLayout;
        this.rowType = rowType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //  ViewBinding for a clean, stable reference to all views
        ItemMovieBinding binding = ItemMovieBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemMovieBinding binding;

        public ViewHolder(@NonNull ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(RowItem item) {
            // 1. Set Title Text
            binding.tvTitle.setText(item.getTitle() != null ? item.getTitle() : "");

            // 2. Calculate Dimensions (Exactly like original logic)
            int width;
            float ratio;
            String imageUrl;

            if ("carousel".equalsIgnoreCase(rowType)) {
                width = dpToPx(400);
                ratio = 0.75f;
                imageUrl = item.getBackground() != null ? item.getBackground() : item.getPoster();

                binding.tvTitle.setVisibility(View.GONE);
                if (binding.infoContainer != null) binding.infoContainer.setVisibility(View.VISIBLE);
                binding.tvMovieName.setText(item.getTitle());

                String genre = (item.getGenre() != null && !item.getGenre().isEmpty()) ? item.getGenre().get(0) : "";
                binding.tvMetaInfo.setText(item.getYear() + " • " + genre + " • " + item.getRuntime());

                if (binding.btnWatchNow != null) binding.btnWatchNow.setVisibility(View.VISIBLE);
                if (binding.bottomShadow != null) binding.bottomShadow.setVisibility(View.VISIBLE);
            } else {
                if (binding.infoContainer != null) binding.infoContainer.setVisibility(View.GONE);
                if (binding.btnWatchNow != null) binding.btnWatchNow.setVisibility(View.GONE);
                if (binding.bottomShadow != null) binding.bottomShadow.setVisibility(View.GONE);
                binding.tvTitle.setVisibility(View.VISIBLE);

                if ("portrait".equalsIgnoreCase(rowLayout)) {
                    width = dpToPx(140);
                    ratio = 1.5f;
                    imageUrl = item.getPortrait();
                } else if ("square".equalsIgnoreCase(rowLayout)) {
                    width = dpToPx(160);
                    ratio = 1f;
                    imageUrl = item.getPortrait();
                } else {
                    width = dpToPx(260);
                    ratio = 9f / 16f;
                    imageUrl = item.getPoster();
                }

                // Apply Dynamic API Ratio if available
                if (item.getTileWidth() != null && item.getTileHeight() != null) {
                    try {
                        float tileW = Integer.parseInt(item.getTileWidth());
                        float tileH = Integer.parseInt(item.getTileHeight());
                        if (tileW > 0 && tileH > 0) {
                            float apiRatio = tileH / tileW;
                            if (apiRatio > 0.1f && apiRatio < 1.8f) ratio = apiRatio;
                        }
                    } catch (Exception ignored) {}
                }
            }

            int height = (int) (width * ratio);

            // 3. Apply Size (Safe LayoutParams handling)
            applySize(binding.cardContainer, width, height);
            applySize(binding.imgPoster, width, height);

            // 4. Load Image
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(new ColorDrawable(Color.parseColor("#121212")))
                    .error(new ColorDrawable(Color.BLACK))
                    .centerCrop()
                    .into(binding.imgPoster);

            // 5. Interactions (Focus & Click)
            setupInteractions(item);
        }

        private void applySize(View view, int w, int h) {
            if (view == null) return;
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.width = w;
            params.height = h;
            view.setLayoutParams(params);
        }

        private void setupInteractions(RowItem item) {
            // Focus Animation
            itemView.setOnFocusChangeListener((v, hasFocus) -> {
                float scale = hasFocus ? 1.1f : 1.0f;
                v.animate().scaleX(scale).scaleY(scale)
                        .translationZ(hasFocus ? 12f : 0f)
                        .setDuration(200).start();
                binding.tvTitle.setTextColor(hasFocus ? Color.WHITE : Color.parseColor("#BBBBBB"));
            });

            // Click Navigation
            View.OnClickListener navigate = v -> {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("ITEM_JSON", new Gson().toJson(item));
                context.startActivity(intent);
            };

            itemView.setOnClickListener(navigate);
            if (binding.cardContainer != null) binding.cardContainer.setOnClickListener(navigate);
            if (binding.btnWatchNow != null) binding.btnWatchNow.setOnClickListener(navigate);
        }
    }

    private int dpToPx(int dp) {
        return Math.round(dp * context.getResources().getDisplayMetrics().density);
    }
}