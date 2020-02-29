package app.vimax.androiddrinkshop.Database.Local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import app.vimax.androiddrinkshop.Database.ModelDB.Cart;
import app.vimax.androiddrinkshop.Database.ModelDB.Favorite;

@Database(entities = {Cart.class, Favorite.class}, version = 1)
public abstract class EDMTRoomDatabase extends RoomDatabase {
    public abstract CartDAO cartDAO();
    public abstract FavoriteDAO favoriteDAO();

    private static EDMTRoomDatabase instance;

    public static EDMTRoomDatabase getInstance(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context, EDMTRoomDatabase.class, "EDMT_DrinkShopDB")
                    .allowMainThreadQueries()
                    .build();
        return instance;
    }
}
