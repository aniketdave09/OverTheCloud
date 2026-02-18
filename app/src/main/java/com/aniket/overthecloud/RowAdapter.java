package com.aniket.overthecloud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aniket.overthecloud.data.model.RowModel;

import java.util.List;

public class RowAdapter extends RecyclerView.Adapter<RowAdapter.RowViewHolder> {

    private Context context;
    private List<RowModel> rowList;

    public RowAdapter(Context context, List<RowModel> rowList) {
        this.context = context;
        this.rowList = rowList;
    }

    @NonNull
    @Override
    public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_row, parent, false);

        return new RowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowViewHolder holder, int position) {

        RowModel row = rowList.get(position);

        // Set row title
        holder.rowTitle.setText(row.getRowTitle());

        // Setup horizontal RecyclerView
        holder.horizontalRecyclerView.setLayoutManager(
                new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        );

        // Attach MovieAdapter
        MovieAdapter movieAdapter = new MovieAdapter(context, row.getMovieList());
        holder.horizontalRecyclerView.setAdapter(movieAdapter);
    }

    @Override
    public int getItemCount() {
        return rowList.size();
    }

    public static class RowViewHolder extends RecyclerView.ViewHolder {

        TextView rowTitle;
        RecyclerView horizontalRecyclerView;

        public RowViewHolder(@NonNull View itemView) {
            super(itemView);

            rowTitle = itemView.findViewById(R.id.rowTitle);
            horizontalRecyclerView = itemView.findViewById(R.id.horizontalRecyclerView);
        }
    }
}

