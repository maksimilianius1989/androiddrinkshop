package app.vimax.androiddrinkshop.Database.DataSource;

import java.util.List;

import app.vimax.androiddrinkshop.Database.ModelDB.Favorite;
import io.reactivex.Flowable;

public class FavoriteRepository implements IFavoriteDataSource {
    private IFavoriteDataSource favoriteDataSource;

    private static FavoriteRepository instance;

    public FavoriteRepository(IFavoriteDataSource favoriteDataSource) {
        this.favoriteDataSource = favoriteDataSource;
    }

    public static FavoriteRepository getInstance(IFavoriteDataSource favoriteDataSource) {
        if(instance == null)
            instance = new FavoriteRepository(favoriteDataSource);
        return instance;
    }

    @Override
    public Flowable<List<Favorite>> getFavItems() {
        return favoriteDataSource.getFavItems();
    }

    @Override
    public int isFavorite(int itemId) {
        return favoriteDataSource.isFavorite(itemId);
    }

    @Override
    public void delete(Favorite favorite) {
        favoriteDataSource.delete(favorite);
    }
}
