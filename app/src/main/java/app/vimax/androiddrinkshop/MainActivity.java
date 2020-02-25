package app.vimax.androiddrinkshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

import app.vimax.androiddrinkshop.Model.CheckUserResponse;
import app.vimax.androiddrinkshop.Model.User;
import app.vimax.androiddrinkshop.Retrofit.IDrinkShopAPI;
import app.vimax.androiddrinkshop.Utils.Common;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TEST_PHONE = "1234567892";
    Button btn_continue;
    IDrinkShopAPI mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mService = Common.getAPI();

        btn_continue = (Button) findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoginPage();
            }
        });
    }

    private void startLoginPage() {
        final AlertDialog alertDialog = new SpotsDialog.Builder().setContext(MainActivity.this).build();
        alertDialog.show();
        alertDialog.setMessage("Please waiting...");

        mService.checkUserExists(TEST_PHONE)
                .enqueue(new Callback<CheckUserResponse>() {
                    @Override
                    public void onResponse(Call<CheckUserResponse> call, Response<CheckUserResponse> response) {
                        CheckUserResponse userResponse = response.body();
                        alertDialog.dismiss();

                        if (userResponse.isExists()) {
                            // if user already exists, just start new Activity

                        } else {
                            // else, need register
                            showRegisterDialog(TEST_PHONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckUserResponse> call, Throwable t) {

                    }
                });


    }

    private void showRegisterDialog(final String phone) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("REGISTER");

        LayoutInflater inflater = this.getLayoutInflater();
        View register_layout = inflater.inflate(R.layout.register_layout, null);

        final MaterialEditText edt_name = (MaterialEditText) register_layout.findViewById(R.id.edt_name);
        final MaterialEditText edt_address = (MaterialEditText) register_layout.findViewById(R.id.edt_address);
        final MaterialEditText edt_birthdate = (MaterialEditText) register_layout.findViewById(R.id.edt_birthdate);

        edt_birthdate.addTextChangedListener(new PatternedTextWatcher("####-##-##"));

        Button btn_register = (Button) register_layout.findViewById(R.id.btn_register);

        builder.setView(register_layout);

        final AlertDialog dialog = builder.create();;
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                if (TextUtils.isEmpty(edt_address.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Please enter your address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edt_birthdate.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Please enter your birthdate", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edt_name.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                    return;
                }

                final AlertDialog watingDialog = new SpotsDialog.Builder().setContext(MainActivity.this).build();
                watingDialog.show();
                watingDialog.setMessage("Please waiting...");

                mService.registerNewUser(phone,
                            edt_name.getText().toString(),
                            edt_address.getText().toString(),
                            edt_birthdate.getText().toString())
                        .enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                watingDialog.dismiss();
                                User user = response.body();
                                if (TextUtils.isEmpty(user.getError_msg())){
                                    Toast.makeText(MainActivity.this, "User register seccessfully", Toast.LENGTH_SHORT).show();
                                    // Start new activity
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                watingDialog.dismiss();

                            }
                        });
            }
        });

        dialog.show();
    }
}
