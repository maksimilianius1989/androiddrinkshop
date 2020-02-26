package app.vimax.androiddrinkshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.vimax.androiddrinkshop.DrinkActivity;
import app.vimax.androiddrinkshop.Interface.IItemClickListener;
import app.vimax.androiddrinkshop.Model.Category;
import app.vimax.androiddrinkshop.R;
import app.vimax.androiddrinkshop.Utils.Common;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
    Context context;
    List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.menu_item_layout, null);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, final int position) {
        Picasso.with(context)
                .load(categories.get(position).Link)
                .into(holder.img_product);

        holder.txt_menu_name.setText(categories.get(position).Name);

        holder.setItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View v) {
                Common.currentCategory = categories.get(position);

                // start new activity
                context.startActivity(new Intent(context, DrinkActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
