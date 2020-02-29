package app.vimax.androiddrinkshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
    private static final int REQUEST_PERMISSION = 1001;
    Button btn_continue;
    IDrinkShopAPI mService;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case REQUEST_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, REQUEST_PERMISSION);
        }

        mService = Common.getAPI();

        btn_continue = (Button) findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoginPage();
            }
        });

        startLoginPage();
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
                            //Fetch information
                            mService.getUserInformation(TEST_PHONE)
                                    .enqueue(new Callback<User>() {
                                        @Override
                                        public void onResponse(Call<User> call, Response<User> response) {
                                            Common.currentUser = response.body();

                                            // if user already exists, just start new Activity
                                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                            finish();
                                        }

                                        @Override
                                        public void onFailure(Call<User> call, Throwable t) {
                                            Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            // else, need register
                            showRegisterDialog(TEST_PHONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckUserResponse> call, Throwable t) {
                        Log.e("MYERROR", "DISCONNECT TO SERVER");
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
                                    Common.currentUser = response.body();
                                    // Start new activity
                                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                    finish();
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
