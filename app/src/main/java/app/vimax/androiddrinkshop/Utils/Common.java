package app.vimax.androiddrinkshop.Utils;

import java.util.ArrayList;
import java.util.List;

import app.vimax.androiddrinkshop.Database.DataSource.CartRepository;
import app.vimax.androiddrinkshop.Database.DataSource.FavoriteRepository;
import app.vimax.androiddrinkshop.Database.Local.EDMTRoomDatabase;
import app.vimax.androiddrinkshop.Model.Category;
import app.vimax.androiddrinkshop.Model.Drink;
import app.vimax.androiddrinkshop.Model.User;
import app.vimax.androiddrinkshop.Retrofit.IDrinkShopAPI;
import app.vimax.androiddrinkshop.Retrofit.RetrofitClient;

public class Common {
    public static final String BASE_URL = "http://wos.kl.com.ua/";

    public static final String TOPPING_MENU_ID = "2";

    public static User currentUser = null;
    public static Category currentCategory =  null;

    public static List<Drink> toppingList = new ArrayList<>();

    public static double toppingPrice = 0.0;
    public static List<String> toppingAdded = new ArrayList<>();

    //Hold field
    public static int sizeOfCup = -1; // -1: no choose (error), 0 : M, 1 : L
    public static int sugar = -1; // -1 : no choose (error)
    public static int ice = -1;

    //Database
    public static EDMTRoomDatabase edmtRoomDatabase;
    public static CartRepository cartRepository;
    public static FavoriteRepository favoriteRepository;

    public static IDrinkShopAPI getAPI()
    {
        return RetrofitClient.getClient(BASE_URL).create(IDrinkShopAPI.class);
    }
}
