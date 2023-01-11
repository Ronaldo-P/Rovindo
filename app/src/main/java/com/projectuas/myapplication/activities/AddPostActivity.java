package com.projectuas.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.projectuas.myapplication.databinding.ActivityAddPostBinding;
import com.projectuas.myapplication.models.ValueNoData;
import com.projectuas.myapplication.services.APIServices;
import com.projectuas.myapplication.utils.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPostActivity extends AppCompatActivity {
    private ActivityAddPostBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.progressbar.setVisibility(View.GONE);

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = binding.etContent.getText().toString();

                boolean bolehPost = true;
                if(TextUtils.isEmpty(content))
                {
                    bolehPost = false;
                    binding.etContent.setError("Konten Tidak Boleh Kosong!");
                }
                if(bolehPost)
                {
//                        addPost(Utility.getValue(AddPostActivity.this,"xUsername"), content);
                    addPost(content);
                }
            }
        });
    }

    private void addPost( String content) {
//        Toast.makeText(this, "namanya : " + username, Toast.LENGTH_SHORT).show();
        binding.progressbar.setVisibility(View.VISIBLE);
        APIServices api = Utility.getmRetrofit().create(APIServices.class);
        Call<ValueNoData> call = api.addBarang("dirumahaja",content);
        call.enqueue(new Callback<ValueNoData>() {
            @Override
            public void onResponse(Call<ValueNoData> call, Response<ValueNoData> response) {
                if (response.code() == 200)
                {
                    binding.progressbar.setVisibility(View.GONE);
                    int success = response.body().getSuccess();
                    String message = response.body().getMessage();
                    if(success == 1)
                    {
                        Toast.makeText(AddPostActivity.this, message, Toast.LENGTH_SHORT).show();
                        finish();
                    }else
                    {
                        Toast.makeText(AddPostActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    binding.progressbar.setVisibility(View.GONE);
                    Toast.makeText(AddPostActivity.this, "Response " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ValueNoData> call, Throwable t) {
                binding.progressbar.setVisibility(View.GONE);
                System.out.println("Retrofit Error : " + t.getMessage());
                Toast.makeText(AddPostActivity.this, "Retrofit Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}