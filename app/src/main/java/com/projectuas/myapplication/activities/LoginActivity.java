package com.projectuas.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.projectuas.myapplication.databinding.ActivityLoginBinding;
import com.projectuas.myapplication.models.ValueNoData;
import com.projectuas.myapplication.services.APIServices;
import com.projectuas.myapplication.utils.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.progressBar.setVisibility((View.GONE));
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.etUsername.getText().toString();
                String password = binding.etPassword.getText().toString();

                boolean bolehLogin = true;
                if(TextUtils.isEmpty(username))
                {
                    bolehLogin = false;
                    binding.etUsername.setError("Username tidak boleh kosong!");
                }
                if(TextUtils.isEmpty(password))
                {
                    bolehLogin = false;
                    binding.etPassword.setError("Password tidak boleh kosong!");
                }
                if (bolehLogin)
                {
                    login(username,password);
                }
            }
        });
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void login(String username, String password) {
        binding.progressBar.setVisibility(View.VISIBLE);
        APIServices api = Utility.getmRetrofit().create(APIServices.class);
        Call<ValueNoData> call = api.login("dirumahaja",username,password);
        call.enqueue(new Callback<ValueNoData>() {
            @Override
            public void onResponse(Call<ValueNoData> call, Response<ValueNoData> response) {
                if(response.code() == 200)
                {
                    int success = response.body().getSuccess();
                    String message = response.body().getMessage();
                    if (success == 1)
                    {
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                        Utility.setValue(LoginActivity.this,"xUsername",username);
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }else {
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Response " + response.code(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ValueNoData> call, Throwable t) {
                binding.progressBar.setVisibility(View.VISIBLE);
                System.out.println("Retrofit Error : " + t.getMessage());
                Toast.makeText(LoginActivity.this, "Retrofit Error " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}