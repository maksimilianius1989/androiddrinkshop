package app.vimax.androiddrinkshop.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import app.vimax.androiddrinkshop.Interface.IItemClickListener;
import app.vimax.androiddrinkshop.Model.Drink;
import app.vimax.androiddrinkshop.R;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkViewHolder> {
    Context context;
    List<Drink> drinkList;

    public DrinkAdapter(Context context, List<Drink> drinkList) {
        this.context = context;
        this.drinkList = drinkList;
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.drink_item_layout, null);
        return new DrinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, final int position) {
        holder.txt_price.setText(new StringBuilder("$").append(drinkList.get(position).Price.toString()));
        holder.txt_drink_name.setText(drinkList.get(position).Name);

        holder.btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddToCartDialog(position);
            }
        });

        Picasso.with(context)
                .load(drinkList.get(position).Link)
                .into(holder.img_product);

        holder.setItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddToCartDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.add_to_cart_layout, null);

        ImageView img_product_dialog = (ImageView) itemView.findViewById(R.id.img_cart_product);
        ElegantNumberButton txt_count = (ElegantNumberButton) itemView.findViewById(R.id.txt_count);
        TextView txt_product_dialog = (TextView) itemView.findViewById(R.id.txt_cart_product_name);

        EditText edit_comment = (EditText) itemView.findViewById(R.id.edt_comment);

        RadioButton rdi_sizeM = (RadioButton) itemView.findViewById(R.id.rdi_sizeM);
        RadioButton rdi_sizeL = (RadioButton) itemView.findViewById(R.id.rdi_sizeL);

        RadioButton rdi_sugar_100 = (RadioButton) itemView.findViewById(R.id.rdi_sugar_100);
        RadioButton rdi_sugar_70 = (RadioButton) itemView.findViewById(R.id.rdi_sugar_70);
        RadioButton rdi_sugar_50 = (RadioButton) itemView.findViewById(R.id.rdi_sugar_50);
        RadioButton rdi_sugar_30 = (RadioButton) itemView.findViewById(R.id.rdi_sugar_30);
        RadioButton rdi_sugar_free = (RadioButton) itemView.findViewById(R.id.rdi_sugar_free);

        RadioButton rdi_ice_100 = (RadioButton) itemView.findViewById(R.id.rdi_ice_100);
        RadioButton rdi_ice_70 = (RadioButton) itemView.findViewById(R.id.rdi_ice_70);
        RadioButton rdi_ice_50 = (RadioButton) itemView.findViewById(R.id.rdi_ice_50);
        RadioButton rdi_ice_30 = (RadioButton) itemView.findViewById(R.id.rdi_ice_30);
        RadioButton rdi_ice_free = (RadioButton) itemView.findViewById(R.id.rdi_ice_free);

        //set data
        Picasso.with(context)
                .load(drinkList.get(position).Link)
                .into(img_product_dialog);
        txt_product_dialog.setText(drinkList.get(position).Name);

        builder.setView(itemView);
        builder.setNegativeButton("ADD TO CART", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        builder.show();
    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }
}
