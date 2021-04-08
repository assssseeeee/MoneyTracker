package com.example.moneytracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewViewHolder> {

    private ArrayList<RecyclerViewItem> arrayList;

    public static class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextViewCardView;
        TextView itemPriceTextViewCardView;
        TextView itemDateTextViewCardView;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextViewCardView = itemView.findViewById(R.id.itemNameTextViewCardView);
            itemDateTextViewCardView = itemView.findViewById(R.id.itemDateTextViewCardView);
            itemPriceTextViewCardView = itemView.findViewById(R.id.itemPriceTextViewCardView);
        }
    }

    public RecyclerViewAdapter(ArrayList<RecyclerViewItem> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent,
                false);
        RecyclerViewViewHolder recyclerViewViewHolder = new RecyclerViewViewHolder(view);
        return recyclerViewViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewViewHolder holder, int position) {
        RecyclerViewItem recyclerViewItem = arrayList.get(position);
        holder.itemNameTextViewCardView.setText(recyclerViewItem.getItemNameTextViewCardViewString());
        holder.itemDateTextViewCardView.setText(recyclerViewItem.getItemDateTextViewCardViewString());
        holder.itemPriceTextViewCardView.setText(recyclerViewItem.getItemPriceTextViewCardViewString());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
