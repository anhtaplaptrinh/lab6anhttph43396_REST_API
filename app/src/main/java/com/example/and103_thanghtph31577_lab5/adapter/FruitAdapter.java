package com.example.and103_thanghtph31577_lab5.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.and103_thanghtph31577_lab5.R;
import com.example.and103_thanghtph31577_lab5.databinding.ItemFruitBinding;
import com.example.and103_thanghtph31577_lab5.model.Fruit;

import java.util.ArrayList;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Fruit> list;
    private FruitClick fruitClick;

    public FruitAdapter(Context context, ArrayList<Fruit> list, FruitClick fruitClick) {
        this.context = context;
        this.list = list;
        this.fruitClick = fruitClick;
    }

    public interface FruitClick {
        void delete(Fruit fruit);
        void edit(Fruit fruit);
        void showDetail(Fruit fruit);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFruitBinding binding = ItemFruitBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list != null && !list.isEmpty() && position < list.size()) {
            Fruit fruit = list.get(position);
            holder.binding.tvName.setText(fruit.getName());
            holder.binding.tvPriceQuantity.setText("price :" + fruit.getPrice() + " - quantity:" + fruit.getQuantity());
            holder.binding.tvDes.setText(fruit.getDescription());

            if (fruit.getImage() != null && !fruit.getImage().isEmpty()) {
                String url = fruit.getImage().get(0);
                String newUrl = url.replace("localhost", "10.0.2.2");
                Glide.with(context)
                        .load(newUrl)
                        .thumbnail(Glide.with(context).load(R.drawable.baseline_broken_image_24))
                        .into(holder.binding.img);

                Log.d("321321", "onBindViewHolder: " + list.get(position).getImage().get(0));
            } else {
                // Handle the case where the image list is empty
                Glide.with(context)
                        .load(R.drawable.baseline_broken_image_24)
                        .into(holder.binding.img);
            }

            holder.binding.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fruitClick.edit(fruit);
                }
            });

            holder.binding.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fruitClick.delete(fruit);
                }
            });
        } else {
            // Handle the case where the list is null or empty
            Log.e("FruitAdapter", "Fruit list is null or empty at position: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemFruitBinding binding;

        public ViewHolder(ItemFruitBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
