package com.aniket.overthecloud.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aniket.overthecloud.data.model.Row;
import com.aniket.overthecloud.databinding.ItemRowBinding;
import java.util.List;

public class RowAdapter extends RecyclerView.Adapter<RowAdapter.ViewHolder> {

    private final List<Row> rowList;
    private final Context context;

    public RowAdapter(Context context, List<Row> rowList) {
        this.context = context;
        this.rowList = rowList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRowBinding binding = ItemRowBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Row row = rowList.get(position);
        holder.bind(row, context);
    }

    @Override
    public int getItemCount() {
        return rowList != null ? rowList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemRowBinding binding;

        public ViewHolder(@NonNull ItemRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Row row, Context context) {
            binding.tvHeader.setText(row.getRowHeader() != null ? row.getRowHeader() : "");

            // Horizontal List Setup
            if (binding.rvItems.getLayoutManager() == null) {
                binding.rvItems.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            }

            binding.rvItems.setFocusable(false);
            binding.rvItems.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);

            // Pass the data to the inner adapter
            RowItemAdapter adapter = new RowItemAdapter(context, row.getRowItems(), row.getRowLayout(), row.getRowType());
            binding.rvItems.setAdapter(adapter);
        }
    }
}