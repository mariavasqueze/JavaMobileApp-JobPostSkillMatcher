package com.example.skillsmatcher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter <Adapter.ViewHolder>  {
    private ArrayList<String[]> listings;

    private final RecyclerViewInterface recyclerViewInterface;

    // constructor
    public Adapter (ArrayList<String[]> data, RecyclerViewInterface recyclerViewInterface) {
        this.listings = data;
        this.recyclerViewInterface = recyclerViewInterface;

    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView desc;
        public ViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            this.title = (TextView) itemView.findViewById(R.id.txt_title);
            this.desc = (TextView) itemView.findViewById(R.id.txt_desc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        // check if position is valid
                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {

        final String[] listing = listings.get(position);
        holder.title.setText(listing[0]);
        holder.desc.setText(listing[1]);
//        Log.d("recycler: ",position + " " + listing[0]);
    }

    @Override
    public int getItemCount() {
        return listings.size();
    }
}
