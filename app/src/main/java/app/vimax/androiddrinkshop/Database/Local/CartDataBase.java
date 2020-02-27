package app.vimax.androiddrinkshop.Database.Local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import app.vimax.androiddrinkshop.Database.ModelDB.Cart;

@Database(entities = {Cart.class}, version = 1)
public abstract class CartDataBase extends RoomDatabase {
    public abstract CartDAO cartDAO();
    private static CartDataBase instance;

    public static CartDataBase getInstance(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context, CartDataBase.class, "EDMT_DrinkShopDB")
                    .allowMainThreadQueries()
                    .build();
        return instance;
    }
}
