package app.vimax.androiddrinkshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import app.vimax.androiddrinkshop.Adapter.CartAdapter;
import app.vimax.androiddrinkshop.Adapter.FavoriteAdapter;
import app.vimax.androiddrinkshop.Database.ModelDB.Cart;
import app.vimax.androiddrinkshop.Database.ModelDB.Favorite;
import app.vimax.androiddrinkshop.Utils.Common;
import app.vimax.androiddrinkshop.Utils.RecyclerItemTouchHelper;
import app.vimax.androiddrinkshop.Utils.RecyclerItemTouchHelperListener;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CartActivity extends AppCompatActivity implements RecyclerItemTouchHelperListener {

    RecyclerView recycler_cart;
    Button btn_place_order;

    List<Cart> cartList = new ArrayList<>();
    CartAdapter cartAdapter;

    CompositeDisposable compositeDisposable;

    RelativeLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        compositeDisposable = new CompositeDisposable();

        recycler_cart = (RecyclerView) findViewById(R.id.recycler_cart);
        recycler_cart.setLayoutManager(new LinearLayoutManager(this));
        recycler_cart.setHasFixedSize(true);

        ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recycler_cart);

        btn_place_order = (Button) findViewById(R.id.btn_place_order);

        rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);

        loadCartItems();
    }

    private void loadCartItems() {
        compositeDisposable.add(
                Common.cartRepository.getCartItems()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Cart>>() {
                    @Override
                    public void accept(List<Cart> carts) throws Exception {
                        displayCartItem(carts);
                    }
                })
        );
    }

    private void displayCartItem(List<Cart> carts) {
        cartList = carts;
        cartAdapter = new CartAdapter(this, carts);
        recycler_cart.setAdapter(cartAdapter);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCartItems();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CartAdapter.CartViewHolder) {
            String name = cartList.get(viewHolder.getAdapterPosition()).name;

            final Cart deleteItem = cartList.get(viewHolder.getAdapterPosition());
            final int deleteIndex = viewHolder.getAdapterPosition();

            //delete item from adapter
            cartAdapter.removeItem(deleteIndex);
            //delete item from room database
            Common.cartRepository.deleteCartItem(deleteItem);

            Snackbar snackbar = Snackbar.make(
                    rootLayout,
                    new StringBuilder(name).append(" removed from Favorites List").toString(),
                    Snackbar.LENGTH_LONG
            );
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cartAdapter.restoreItem(deleteItem, deleteIndex);
                    Common.cartRepository.insertToCart(deleteItem);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}
