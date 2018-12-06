package com.example.allan.citizenhero;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.allan.citizenhero.DTOs.CallDTO;
import com.google.gson.Gson;

public class Preferences {

    public static void savePreferences(Context context, CallDTO call){
        SharedPreferences preferences = context.getSharedPreferences("formulario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(call);
        editor.putString("chamado", json);
        editor.apply();
    }
}
