package app.vimax.androiddrinkshop.Database.DataSource;

import androidx.room.Delete;
import androidx.room.Query;

import java.util.List;

import app.vimax.androiddrinkshop.Database.ModelDB.Favorite;
import io.reactivex.Flowable;

public interface IFavoriteDataSource {
    @Query("SELECT * FROM Favorite")
    Flowable<List<Favorite>> getFavItems();

    @Query("SELECT EXISTS (SELECT 1 FROM Favorite WHERE id=:itemId)")
    int isFavorite(int itemId);

    @Delete
    void delete(Favorite favorite);
}
