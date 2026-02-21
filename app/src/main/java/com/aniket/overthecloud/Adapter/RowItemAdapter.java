package com.aniket.overthecloud.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aniket.overthecloud.R;
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

        int width = dpToPx(150);   // default
        int height = dpToPx(200);
        String imageUrl;

        // =========================
        // 1. IMAGE SELECTION
        // =========================
        if ("portrait".equalsIgnoreCase(rowLayout)) {
            imageUrl = item.getPortrait();
        } else {
            imageUrl = item.getPoster();
        }

        // =========================
        // 2. SIZE FROM API (ratio based)
        // =========================
        if (item.getTileWidth() != null && item.getTileHeight() != null) {
            try {
                int tileW = Integer.parseInt(item.getTileWidth());
                int tileH = Integer.parseInt(item.getTileHeight());

                int baseWidth;

                if ("carousel".equalsIgnoreCase(rowType)) {
                    baseWidth = dpToPx(800);   // wide banner
                } else if ("portrait".equalsIgnoreCase(rowLayout)) {
                    baseWidth = dpToPx(140);
                } else if ("square".equalsIgnoreCase(rowLayout)) {
                    baseWidth = dpToPx(160);
                } else {
                    baseWidth = dpToPx(220);   // landscape
                }

                width = baseWidth;
                height = (int) (baseWidth * (tileH / (float) tileW));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // =========================
        // 3. TITLE VISIBILITY
        // =========================
        if ("carousel".equalsIgnoreCase(rowType)) {
            holder.tvTitle.setVisibility(View.GONE);
        } else {
            holder.tvTitle.setVisibility(View.VISIBLE);
        }

        // =========================
        // 4. VERY IMPORTANT (FIX YOUR ISSUE)
        // =========================

        // 👉 Update parent (itemView)
        ViewGroup.LayoutParams parentParams = holder.itemView.getLayoutParams();
        if (parentParams != null) {
            parentParams.width = width;
            parentParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            holder.itemView.setLayoutParams(parentParams);
        }

        // 👉 Update ImageView
        ViewGroup.LayoutParams imageParams = holder.imgPoster.getLayoutParams();
        imageParams.width = width;
        imageParams.height = height;
        holder.imgPoster.setLayoutParams(imageParams);

        // =========================
        // 5. LOAD IMAGE
        // =========================
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .centerCrop()
                .into(holder.imgPoster);

        // =========================
        // 6. FOCUS EFFECT
        // =========================
        holder.itemView.setFocusable(true);
        holder.itemView.setClickable(true);

        holder.itemView.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                v.animate().scaleX(1.1f).scaleY(1.1f).setDuration(200).start();
                v.setBackgroundResource(R.drawable.item_selector);
                holder.tvTitle.setSelected(true);
                v.setZ(10f);
            } else {
                v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200).start();
                v.setBackgroundResource(0);
                holder.tvTitle.setSelected(false);
                v.setZ(0f);
            }
        });

        // =========================
        // 7. CLICK
        // =========================
        holder.itemView.setOnClickListener(v -> {
            // TODO
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }
}