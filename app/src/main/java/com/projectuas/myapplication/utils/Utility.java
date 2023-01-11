package com.projectuas.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.security.GeneralSecurityException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utility {
    private  static final String PREFERENCE_FILE_KEY = "myAppPreference";
    private static final String BASE_URL = "https://bisaprojectuas.000webhostapp.com/uas/index.php/MobileControl/";

    public static Retrofit mRetrofit;

    public static Retrofit getmRetrofit()
    {

        if(mRetrofit == null)
        {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    public static void setValue(Context context, String xPref, String xValue)
    {
        SharedPreferences sp = null;
        try
        {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
             sp = EncryptedSharedPreferences.create(
                    PREFERENCE_FILE_KEY,
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
              );
        }catch (GeneralSecurityException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(xPref, xValue);
        editor.commit();

    }

    public static String getValue(Context context, String xPref)
    {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        String xValue = sp.getString(xPref,null);
        return xValue;

    }
    public static void clearUser(Context context)
    {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences sp = null;
        try
        {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            sp = EncryptedSharedPreferences.create(
                    PREFERENCE_FILE_KEY,
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        }catch (GeneralSecurityException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("xUsername", null);
        editor.apply();
    }

    public static boolean checkValue(Context context, String xPref)
    {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_FILE_KEY, context.MODE_PRIVATE);
        SharedPreferences sp = null;
        try
        {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            sp = EncryptedSharedPreferences.create(
                    PREFERENCE_FILE_KEY,
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        }catch (GeneralSecurityException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        String xValue = sp.getString(xPref, null);
        if(xValue != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
