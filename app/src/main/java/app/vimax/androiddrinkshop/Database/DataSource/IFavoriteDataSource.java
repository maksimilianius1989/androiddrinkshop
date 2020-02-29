package app.vimax.androiddrinkshop.Database.DataSource;

import androidx.room.Delete;
import androidx.room.Query;

import java.util.List;

import app.vimax.androiddrinkshop.Database.ModelDB.Favorite;
import io.reactivex.Flowable;

public interface IFavoriteDataSource {
    Flowable<List<Favorite>> getFavItems();

    int isFavorite(int itemId);

    void insertFav(Favorite...favorites);

    void delete(Favorite favorite);
}
