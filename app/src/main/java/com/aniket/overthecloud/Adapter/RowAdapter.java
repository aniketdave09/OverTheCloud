package com.aniket.overthecloud.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aniket.overthecloud.R;
import com.aniket.overthecloud.data.model.Row;

import java.util.List;

public class RowAdapter extends RecyclerView.Adapter<RowAdapter.ViewHolder> {

    private List<Row> rowList;
    private Context context;

    public RowAdapter(Context context, List<Row> rowList) {
        this.context = context;
        this.rowList = rowList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Row row = rowList.get(position);

        // Set header
        holder.tvHeader.setText(row.getRowHeader() != null ? row.getRowHeader() : "");

        // Setup horizontal RecyclerView
        RowItemAdapter adapter = new RowItemAdapter(context, row.getRowItems(),row.getRowLayout(),row.getRowType());

        holder.rvItems.setLayoutManager(
                new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        );

        holder.rvItems.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return rowList != null ? rowList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvHeader;
        RecyclerView rvItems;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHeader = itemView.findViewById(R.id.tvHeader);
            rvItems = itemView.findViewById(R.id.rvItems);
        }
    }
}

//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.aniket.overthecloud.R;
//import com.aniket.overthecloud.data.model.RowModel;
//
//import java.util.List;
//
//public class RowAdapter extends RecyclerView.Adapter<RowAdapter.RowViewHolder> {
//
//    private Context context;
//    private List<RowModel> rowList;
//
//    public RowAdapter(Context context, List<RowModel> rowList) {
//        this.context = context;
//        this.rowList = rowList;
//    }
//
//    @NonNull
//    @Override
//    public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        View view = LayoutInflater.from(context)
//                .inflate(R.layout.item_row, parent, false);
//
//        return new RowViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RowViewHolder holder, int position) {
//
//        RowModel row = rowList.get(position);
//
//        // Set row title
//        holder.rowTitle.setText(row.getRowTitle());
//
//        // Setup horizontal RecyclerView
//        holder.horizontalRecyclerView.setLayoutManager(
//                new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        );
//
//        // Attach MovieAdapter
//        MovieAdapter movieAdapter = new MovieAdapter(context, row.getMovieList());
//        holder.horizontalRecyclerView.setAdapter(movieAdapter);
//    }
//
//    @Override
//    public int getItemCount() {
//        return rowList.size();
//    }
//
//    public static class RowViewHolder extends RecyclerView.ViewHolder {
//
//        TextView rowTitle;
//        RecyclerView horizontalRecyclerView;
//
//        public RowViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            rowTitle = itemView.findViewById(R.id.rowTitle);
//            horizontalRecyclerView = itemView.findViewById(R.id.horizontalRecyclerView);
//        }
//    }
//}
//