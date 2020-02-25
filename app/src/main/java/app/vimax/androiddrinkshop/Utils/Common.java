package app.vimax.androiddrinkshop.Utils;

import app.vimax.androiddrinkshop.Model.User;
import app.vimax.androiddrinkshop.Retrofit.IDrinkShopAPI;
import app.vimax.androiddrinkshop.Retrofit.RetrofitClient;

public class Common {
    private static final String BASE_URL = "http://wos.kl.com.ua";

    public static User currentUser = null;

    public static IDrinkShopAPI getAPI()
    {
        return RetrofitClient.getClient(BASE_URL).create(IDrinkShopAPI.class);
    }
}
