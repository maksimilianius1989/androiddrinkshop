package app.vimax.androiddrinkshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import app.vimax.androiddrinkshop.Adapter.DrinkAdapter;
import app.vimax.androiddrinkshop.Model.Drink;
import app.vimax.androiddrinkshop.Retrofit.IDrinkShopAPI;
import app.vimax.androiddrinkshop.Utils.Common;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DrinkActivity extends AppCompatActivity {
    IDrinkShopAPI mService;

    RecyclerView lst_drink;

    TextView txt_banner_name;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        mService = Common.getAPI();

        lst_drink = (RecyclerView) findViewById(R.id.recycler_drinks);
        lst_drink.setLayoutManager(new GridLayoutManager(this, 2));
        lst_drink.setHasFixedSize(true);

        txt_banner_name = (TextView) findViewById(R.id.txt_menu_name);
        txt_banner_name.setText(Common.currentCategory.Name);

        loadListDrink(Common.currentCategory.ID);
    }

    private void loadListDrink(String menuId) {
        compositeDisposable.add(mService.getDrink(menuId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<Drink>>() {
            @Override
            public void accept(List<Drink> drinks) throws Exception {
                displayDrinkList(drinks);
            }
        }));
    }

    private void displayDrinkList(List<Drink> drinks) {
        DrinkAdapter adapter = new DrinkAdapter(this, drinks);
        lst_drink.setAdapter(adapter);
    }

    //Exit Application when click Back button
    boolean isBackButtonClicked = false;

    @Override
    public void onBackPressed() {
        if (isBackButtonClicked) {
            super.onBackPressed();
            return;
        }
        isBackButtonClicked = true;
        Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isBackButtonClicked = false;
    }
}
