package app.vimax.androiddrinkshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.vimax.androiddrinkshop.Model.Drink;
import app.vimax.androiddrinkshop.R;
import app.vimax.androiddrinkshop.Utils.Common;

public class MultiChoiceAdapter extends RecyclerView.Adapter<MultiChoiceAdapter.MultiChoiceViewHolder> {

    Context context;
    List<Drink> optionList;

    public MultiChoiceAdapter(Context context, List<Drink> optionList) {
        this.context = context;
        this.optionList = optionList;
    }

    @NonNull
    @Override
    public MultiChoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.multi_check_layout, null);
        return new MultiChoiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiChoiceViewHolder holder, final int position) {
        holder.checkBox.setText(optionList.get(position).Name);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Common.toppingAdded.add(buttonView.getText().toString());
                    Common.toppingPrice += Double.parseDouble(optionList.get(position).Price);
                } else {
                    Common.toppingAdded.remove(buttonView.getText().toString());
                    Common.toppingPrice -= Double.parseDouble(optionList.get(position).Price);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return optionList.size();
    }

    class MultiChoiceViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;

        public MultiChoiceViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = (CheckBox) itemView.findViewById(R.id.ckb_topping);
        }
    }

}
