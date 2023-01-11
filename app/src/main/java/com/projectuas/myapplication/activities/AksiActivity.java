package com.projectuas.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.projectuas.myapplication.databinding.ActivityAddPostBinding;
import com.projectuas.myapplication.databinding.ActivityAksiBinding;
import com.projectuas.myapplication.models.Barang;
import com.projectuas.myapplication.models.ValueNoData;
import com.projectuas.myapplication.services.APIServices;
import com.projectuas.myapplication.utils.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AksiActivity extends AppCompatActivity {
    private ActivityAksiBinding binding;
    private Barang mPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAksiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        mPost = getIntent().getParcelableExtra("EXTRA_DATA");
        int id = mPost.getId();

        binding.progressbar.setVisibility(View.GONE);
//        binding.etContent.setText(mPost.getStok());

        binding.btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int content = Integer.parseInt(binding.etContent.getText().toString());


                boolean bolehUpdatePost = true;
                if(content == 0)
                {
                    bolehUpdatePost = false;
                    binding.etContent.setError("Konten tidak boleh kosong!");
                }
                if (bolehUpdatePost)
                {
                    tambah(id,content);
                }
            }
        });
        binding.btnKurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int content = Integer.parseInt(binding.etContent.getText().toString());


                boolean bolehUpdatePost = true;
                if(content == 0)
                {
                    bolehUpdatePost = false;
                    binding.etContent.setError("Konten tidak boleh kosong!");
                }
                if (bolehUpdatePost)
                {
                    kurang(id,content);
                }
            }
        });
    }

    private void tambah(int id, int content) {
        binding.progressbar.setVisibility(View.VISIBLE);
        APIServices api = Utility.getmRetrofit().create(APIServices.class);
        Call<ValueNoData> call = api.tambahStok("dirumahaja", id, content);
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
                        Toast.makeText(AksiActivity.this, message, Toast.LENGTH_SHORT).show();
                        finish();
                    }else
                    {
                        Toast.makeText(AksiActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    binding.progressbar.setVisibility(View.GONE);
                    Toast.makeText(AksiActivity.this, "Response " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ValueNoData> call, Throwable t) {
                binding.progressbar.setVisibility(View.GONE);
                System.out.println("Retrofit Error : " + t.getMessage());
                Toast.makeText(AksiActivity.this, "Retrofit Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void kurang(int id, int content) {
        binding.progressbar.setVisibility(View.VISIBLE);
        APIServices api = Utility.getmRetrofit().create(APIServices.class);
        Call<ValueNoData> call = api.kurangStok("dirumahaja", id, content);
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
                        Toast.makeText(AksiActivity.this, message, Toast.LENGTH_SHORT).show();
                        finish();
                    }else
                    {
                        Toast.makeText(AksiActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    binding.progressbar.setVisibility(View.GONE);
                    Toast.makeText(AksiActivity.this, "Response " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ValueNoData> call, Throwable t) {
                binding.progressbar.setVisibility(View.GONE);
                System.out.println("Retrofit Error : " + t.getMessage());
                Toast.makeText(AksiActivity.this, "Retrofit Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}