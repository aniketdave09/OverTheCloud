package com.aniket.overthecloud.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aniket.overthecloud.R;
import com.aniket.overthecloud.UI.DetailActivity;
import com.aniket.overthecloud.data.model.RowItem;
import com.bumptech.glide.Glide;

import java.util.List;

public class RowItemAdapter extends RecyclerView.Adapter<RowItemAdapter.ViewHolder> {

    private List<RowItem> list;
    private Context context;
    private String rowLayout;
    private String rowType;

    public RowItemAdapter(Context context, List<RowItem> list, String rowLayout, String rowType) {
        this.context = context;
        this.list = list;
        this.rowLayout = rowLayout;
        this.rowType = rowType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Ensure R.layout.item_movie exists
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RowItem item = list.get(position);
        holder.tvTitle.setText(item.getTitle() != null ? item.getTitle() : "");

        int width;
        int height;
        float ratio;
        String imageUrl;

        // =========================
        // 1. BASE WIDTH + DEFAULT RATIO
        // =========================
        if ("carousel".equalsIgnoreCase(rowType)) {

            width = dpToPx(320);      // banner width
            ratio = 0.5f;             // default banner ratio (2:1)

            imageUrl = item.getPoster(); // wide image
            holder.tvTitle.setVisibility(View.GONE);

        } else if ("portrait".equalsIgnoreCase(rowLayout)) {

            width = dpToPx(140);
            ratio = 1.5f;             // 2:3

            imageUrl = item.getPortrait();
            holder.tvTitle.setVisibility(View.VISIBLE);

        } else if ("square".equalsIgnoreCase(rowLayout)) {

            width = dpToPx(160);
            ratio = 1f;               // 1:1

            imageUrl = item.getPortrait();
            holder.tvTitle.setVisibility(View.VISIBLE);

        } else {

            // 🔥 FIXED LANDSCAPE
            width = dpToPx(260);
            ratio = 9f / 16f;         // 16:9

            imageUrl = item.getPoster();
            holder.tvTitle.setVisibility(View.VISIBLE);
        }

// =========================
// 2. TRY USING API RATIO (ONLY IF VALID)
// =========================
        if (item.getTileWidth() != null && item.getTileHeight() != null) {
            try {
                int tileW = Integer.parseInt(item.getTileWidth());
                int tileH = Integer.parseInt(item.getTileHeight());

                if (tileW > 0 && tileH > 0) {
                    float apiRatio = (float) tileH / (float) tileW;

                    // 🔥 LOWERED THRESHOLD: 0.15f allows 1920x300
                    // We keep the upper bound at 1.8f for portraits
                    if (apiRatio > 0.1f && apiRatio < 1.8f) {
                        ratio = apiRatio;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // =========================
        // 3. FINAL HEIGHT
        // =========================
        height = (int) (width * ratio);

        // =========================
// 4. APPLY SIZE
// =========================

// Parent (itemView) - Keep width wrap_content so margins work correctly
        ViewGroup.LayoutParams parentParams = holder.itemView.getLayoutParams();
        if (parentParams != null) {
            parentParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            parentParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            holder.itemView.setLayoutParams(parentParams);
        }

// 🔥 IMPORTANT: Update CardView dimensions
        if (holder.cardContainer != null) {
            ViewGroup.LayoutParams cardParams = holder.cardContainer.getLayoutParams();
            cardParams.width = width;
            cardParams.height = height;
            holder.cardContainer.setLayoutParams(cardParams);
        }

// ImageView dimensions
        ViewGroup.LayoutParams imageParams = holder.imgPoster.getLayoutParams();
        imageParams.width = width;
        imageParams.height = height;
        holder.imgPoster.setLayoutParams(imageParams);

// =========================
// 5. LOAD IMAGE
// =========================
        Glide.with(context)
                .load(imageUrl)
                .placeholder(new ColorDrawable(Color.parseColor("#121212"))) // Darker placeholder
                .error(new ColorDrawable(Color.BLACK))
                .centerCrop() // Keep centerCrop so the image fills your calculated ratio
                .into(holder.imgPoster);        // =========================
        // =========================

        //6
        holder.itemView.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                // 1. Scale effect
                holder.cardContainer.animate().scaleX(1.1f).scaleY(1.1f).setDuration(200).start();

                // 2. Show the border overlay
                holder.focusBorder.setVisibility(View.VISIBLE);

                // 3. Bring to front
                v.setZ(10f);
                holder.tvTitle.setTextColor(Color.WHITE);
            } else {
                // 1. Reset scale
                holder.cardContainer.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200).start();

                // 2. Hide the border overlay
                holder.focusBorder.setVisibility(View.GONE);

                // 3. Reset Z and text
                v.setZ(0f);
                holder.tvTitle.setTextColor(Color.parseColor("#BBBBBB"));
            }
        });
        // =========================
        // 7. CLICK
        // =========================
        // Inside onBindViewHolder, Step 7:
        holder.itemView.setOnClickListener(v -> {
            // 1. Convert the current RowItem to a JSON String
            String itemJson = new com.google.gson.Gson().toJson(item);

            // 2. Start DetailActivity
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("ITEM_JSON", itemJson);
            context.startActivity(intent);
        });
    }

    private int dpToPx(int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle;
        androidx.cardview.widget.CardView cardContainer; // Add this
        View focusBorder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            cardContainer = itemView.findViewById(R.id.cardContainer);
            focusBorder = itemView.findViewById(R.id.focusBorder);
        }
    }
}