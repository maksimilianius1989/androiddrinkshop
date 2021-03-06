package app.vimax.androiddrinkshop.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import app.vimax.androiddrinkshop.Interface.IItemClickListener;
import app.vimax.androiddrinkshop.R;

public class DrinkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView img_product;
    TextView txt_drink_name, txt_price;

    IItemClickListener itemClickListener;

    ImageView btn_add_to_cart, btn_favorites;

    public void setItemClickListener(IItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public DrinkViewHolder(@NonNull View itemView) {
        super(itemView);

        img_product = (ImageView) itemView.findViewById(R.id.image_product);
        txt_drink_name = (TextView) itemView.findViewById(R.id.text_drink_name);
        txt_price = (TextView) itemView.findViewById(R.id.text_price);
        btn_add_to_cart = (ImageView) itemView.findViewById(R.id.btn_add_cart);
        btn_favorites = (ImageView) itemView.findViewById(R.id.btn_favorites);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view);
    }
}
