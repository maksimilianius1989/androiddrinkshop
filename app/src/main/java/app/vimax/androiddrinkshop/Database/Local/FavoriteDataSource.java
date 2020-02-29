package app.vimax.androiddrinkshop.Database.Local;

import java.util.List;

import app.vimax.androiddrinkshop.Database.DataSource.IFavoriteDataSource;
import app.vimax.androiddrinkshop.Database.ModelDB.Favorite;
import io.reactivex.Flowable;

public class FavoriteDataSource implements IFavoriteDataSource {
    private FavoriteDAO favoriteDAO;
    private static FavoriteDataSource instance;

    public FavoriteDataSource(FavoriteDAO favoriteDAO) {
        this.favoriteDAO = favoriteDAO;
    }

    public static FavoriteDataSource getInstance(FavoriteDAO favoriteDAO) {
        if (instance == null)
            instance = new FavoriteDataSource(favoriteDAO);
        return instance;
    }

    @Override
    public Flowable<List<Favorite>> getFavItems() {
        return favoriteDAO.getFavItems();
    }

    @Override
    public int isFavorite(int itemId) {
        return favoriteDAO.isFavorite(itemId);
    }

    @Override
    public void delete(Favorite favorite) {
        favoriteDAO.delete(favorite);
    }
}
