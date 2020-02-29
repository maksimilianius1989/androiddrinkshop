package app.vimax.androiddrinkshop.Database.Local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;

import java.util.List;

import app.vimax.androiddrinkshop.Database.ModelDB.Favorite;
import io.reactivex.Flowable;

@Dao
public interface FavoriteDAO {
    @Query("SELECT * FROM Favorite")
    Flowable<List<Favorite>> getFavItems();

    @Query("SELECT EXISTS (SELECT 1 FROM Favorite WHERE id=:itemId)")
    int isFavorite(int itemId);

    @Delete
    void delete(Favorite favorite);
}
