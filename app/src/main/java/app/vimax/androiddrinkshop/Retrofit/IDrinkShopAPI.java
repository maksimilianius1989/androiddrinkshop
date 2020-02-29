package app.vimax.androiddrinkshop.Retrofit;

import java.util.List;
import app.vimax.androiddrinkshop.Model.Banner;
import app.vimax.androiddrinkshop.Model.Category;
import app.vimax.androiddrinkshop.Model.CheckUserResponse;
import app.vimax.androiddrinkshop.Model.Drink;
import app.vimax.androiddrinkshop.Model.User;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IDrinkShopAPI {
    @FormUrlEncoded
    @POST("checkuser.php")
    Call<CheckUserResponse> checkUserExists(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("register.php")
    Call<User> registerNewUser(@Field("phone") String phone,
                                @Field("name") String name,
                                @Field("address") String address,
                                @Field("birthdate") String birthdate);

    @FormUrlEncoded
    @POST("getuser.php")
    Call<User> getUserInformation(@Field("phone") String phone);

    @GET("getbanner.php")
    Observable<List<Banner>> getBanners();

    @GET("getmenu.php")
    Observable<List<Category>> getMenu();

    @FormUrlEncoded
    @POST("getdrink.php")
    Observable<List<Drink>> getDrink(@Field("menuid") String menuID);

    @Multipart
    @POST("upload.php")
    Call<String> uploadFile(@Part MultipartBody.Part phone, @Part MultipartBody.Part file);
}
