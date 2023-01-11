package com.projectuas.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.projectuas.myapplication.databinding.ActivityUpdatePostBinding;
import com.projectuas.myapplication.models.Barang;
import com.projectuas.myapplication.models.Post;
import com.projectuas.myapplication.models.ValueNoData;
import com.projectuas.myapplication.services.APIServices;
import com.projectuas.myapplication.utils.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePostActivity extends AppCompatActivity {
    private ActivityUpdatePostBinding binding;
    private Barang mPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdatePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mPost = getIntent().getParcelableExtra("EXTRA_DATA");
        int id = mPost.getId();

        binding.progressbar.setVisibility(View.GONE);
        binding.etContent.setText(mPost.getNama());

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = binding.etContent.getText().toString();


                boolean bolehUpdatePost = true;
                if(TextUtils.isEmpty(content))
                {
                    bolehUpdatePost = false;
                    binding.etContent.setError("Konten tidak boleh kosong!");
                }
                if (bolehUpdatePost)
                {
                    updatePost(id,content);
                }
            }
        });
    }

    private void updatePost(int id, String content) {
        binding.progressbar.setVisibility(View.VISIBLE);
        APIServices api = Utility.getmRetrofit().create(APIServices.class);
        Call<ValueNoData> call = api.updateBarang("dirumahaja", id, content);
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
                        Toast.makeText(UpdatePostActivity.this, message, Toast.LENGTH_SHORT).show();
                        finish();
                    }else
                    {
                        Toast.makeText(UpdatePostActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    binding.progressbar.setVisibility(View.GONE);
                    Toast.makeText(UpdatePostActivity.this, "Response " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ValueNoData> call, Throwable t) {
                binding.progressbar.setVisibility(View.GONE);
                System.out.println("Retrofit Error : " + t.getMessage());
                Toast.makeText(UpdatePostActivity.this, "Retrofit Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}