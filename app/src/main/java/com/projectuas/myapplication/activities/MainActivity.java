package com.projectuas.myapplication.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.projectuas.myapplication.R;
import com.projectuas.myapplication.adapters.BarangViewAdapter;
import com.projectuas.myapplication.adapters.PostViewAdapter;
import com.projectuas.myapplication.databinding.ActivityMainBinding;
import com.projectuas.myapplication.models.Barang;
import com.projectuas.myapplication.models.Post;
import com.projectuas.myapplication.models.ValueData;
import com.projectuas.myapplication.models.ValueNoData;
import com.projectuas.myapplication.services.APIServices;
import com.projectuas.myapplication.utils.Utility;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements BarangViewAdapter.OnItemLongClickListener {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private RecyclerView rvPost;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Barang> data;
    private BarangViewAdapter postViewAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(!Utility.checkValue(MainActivity.this,"xUsername"))
        {
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }

        setSupportActionBar(binding.toolbar);

        rvPost = findViewById(R.id.rv_post);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);


        rvPost.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPost();
            }
        });

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(MainActivity.this, AddPostActivity.class);
              startActivity(intent);
            }
        });
    }

    protected void onResume()
    {
        super.onResume();
        getPost();
    }
    private void getPost()
    {
        swipeRefreshLayout.setRefreshing(true);
        APIServices api = Utility.getmRetrofit().create(APIServices.class);
        Call<ValueData<Barang>> call = api.getBarang("dirumahaja");
        call.enqueue(new Callback<ValueData<Barang>>() {
            @Override
            public void onResponse(Call<ValueData<Barang>> call, Response<ValueData<Barang>> response) {
                if(response.code() == 200) {
                    swipeRefreshLayout.setRefreshing(false);
                    int success = response.body().getSuccess();
                    String message = response.body().getMessege();
                    if(success == 1)
                    {
//                        Toast.makeText(MainActivity.this, "hell", Toast.LENGTH_SHORT).show();
                        data = response.body().getData();
                        postViewAdapter = new BarangViewAdapter(MainActivity.this,data, MainActivity.this);
                        Toast.makeText(MainActivity.this, "berhasil", Toast.LENGTH_SHORT).show();
                        rvPost.setAdapter(postViewAdapter);
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }else
                {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(MainActivity.this, "Responses " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ValueData<Barang>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("Retrofit Error : " +t.getMessage());
                Toast.makeText(MainActivity.this, "Retrofit Error : " +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Utility.clearUser(MainActivity.this);
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemLongClick(View v,int position) {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this,v);
        popupMenu.inflate(R.menu.menu);
        popupMenu.setGravity(Gravity.RIGHT);
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.action_edit:
                        Intent intent = new Intent(MainActivity.this, UpdatePostActivity.class);
                        intent.putExtra("EXTRA_DATA",data.get(position));
                        startActivity(intent);
                        return true;
                    case R.id.action_aksi:
                        Intent intents = new Intent(MainActivity.this, AksiActivity.class);
                        intents.putExtra("EXTRA_DATA",data.get(position));
                        startActivity(intents);
                        return true;

                    case R.id.action_delete:
                        int id = data.get(position).getId();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        alertDialogBuilder.setTitle("Konfirmasi");
                        alertDialogBuilder.setMessage("Yakin ingin menghapus post '" + data.get(position).getNama() + "'");
                        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deletePost(id);
                            }
                        });
                        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                        deletePost(id);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
//        Toast.makeText(this, "Posisi item klik : " + position, Toast.LENGTH_SHORT).show();
    }

    private void deletePost(int id) {
        APIServices api = Utility.getmRetrofit().create(APIServices.class);
        Call<ValueNoData> call = api.deleteBarang("dirumahaja",id);
        call.enqueue(new Callback<ValueNoData>() {
            @Override
            public void onResponse(Call<ValueNoData> call, Response<ValueNoData> response) {
                if (response.code() == 200)
                {
                    int success = response.body().getSuccess();
                    String message = response.body().getMessage();

                    if(success == 1)
                    {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Response Code : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ValueNoData> call, Throwable t) {
                System.out.println("Retrofit Error : " + t.getMessage());
                Toast.makeText(MainActivity.this, "Retrofit Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    @Override
//    public boolean onSupportNavigateUp() {
////        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
////        return NavigationUI.navigateUp(navController, appBarConfiguration)
////                || super.onSupportNavigateUp();
//    }
}