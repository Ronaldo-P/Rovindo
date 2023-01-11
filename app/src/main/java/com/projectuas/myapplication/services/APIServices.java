package com.projectuas.myapplication.services;

import com.projectuas.myapplication.models.Barang;
import com.projectuas.myapplication.models.Post;
import com.projectuas.myapplication.models.ValueData;
import com.projectuas.myapplication.models.ValueNoData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIServices {
    @POST("loginUser")
    @FormUrlEncoded
    Call<ValueNoData> login(@Field("key") String key,
                            @Field("username") String username,
                            @Field("password") String password);


    @FormUrlEncoded
    @POST("registerUser")
    Call<ValueNoData> register(@Field("key") String key,
                               @Field("username") String username,
                               @Field("password") String password);

@FormUrlEncoded
    @POST("getAllPost")
    Call<ValueData<Post>> getPost(@Field("key") String key);

    @FormUrlEncoded
    @POST("getAllBarang")
    Call<ValueData<Barang>> getBarang(@Field("key") String key);


    @FormUrlEncoded
    @POST("insertBarang")
    Call<ValueNoData> addBarang(@Field("key") String key,
                                @Field("nama") String nama);

    @FormUrlEncoded
    @POST("updateBarang")
    Call<ValueNoData> updateBarang(@Field("key") String key,
                                    @Field("id") int id,
                                   @Field("nama") String nama);
    @FormUrlEncoded
    @POST("deleteBarang ")
    Call<ValueNoData> deleteBarang(@Field("key") String key,
                                 @Field("id") int id);
    @FormUrlEncoded
    @POST("tambahStokBarang ")
    Call<ValueNoData> tambahStok(@Field("key") String key,
                                   @Field("id") int id,
                                   @Field("stok") int stok);
    @FormUrlEncoded
    @POST("kurangStokBarang ")
    Call<ValueNoData> kurangStok(@Field("key") String key,
                                 @Field("id") int id,
                                 @Field("stok") int stok);


    @FormUrlEncoded
    @POST("insertPost")
    Call<ValueNoData> addPost(@Field("key") String key,
                              @Field("username") String username,
                              @Field("content") String content);

    @FormUrlEncoded
    @POST("updatePost")
    Call<ValueNoData> updatePost(@Field("key") String key,
                                 @Field("id") int id,
                                 @Field("content") String content);

    @FormUrlEncoded
    @POST("deletePost ")
    Call<ValueNoData> deletePost(@Field("key") String key,
                                 @Field("id") int id);
}
