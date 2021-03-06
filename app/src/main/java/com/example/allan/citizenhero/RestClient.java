package com.example.allan.citizenhero;

import android.util.Log;

import com.example.allan.citizenhero.DTOs.CallDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

public class RestClient {

    private static final String BASE_URL = "http://10.7.130.16:5000/";

    private static final String CALL_ENDPOINT = BASE_URL + "call/";

    private static final OkHttpClient httpClient = new OkHttpClient();

    private OnRequestListener listener;

    RestClient(){

    }

    public RestClient(OnRequestListener listener){
        this.listener = listener;
    }

    public void doPostCall(CallDTO call) throws IOException {

        Gson gson = new GsonBuilder().create();

        String json_obj = gson.toJson(call);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json_obj);

        Request request = new Request.Builder()
                .url(CALL_ENDPOINT)
                .post(body)
                .build();

        Call httpCall = httpClient.newCall(request);

        httpCall.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d("post", "deu erro" + e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(response.isSuccessful()){
                    Gson gson = new GsonBuilder().create();
                    String str = response.body().string();
                    Log.d("get", str);
                    CallDTO responseCall = gson.fromJson(str, CallDTO.class);
                    listener.onNewCall(responseCall);
                }
            }
        });
    }

    public void doGetCalls(Long id) throws IOException {
        Request request = new Request.Builder().url(CALL_ENDPOINT + String.valueOf(id)).build();

        Call httpCall = httpClient.newCall(request);

        httpCall.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(response.isSuccessful()){
                    Gson gson = new GsonBuilder().create();
                    ArrayList<CallDTO> calls = gson.fromJson(response.body().string(), new TypeToken<ArrayList<CallDTO>>(){}.getType());
                    listener.onUpdate(calls);
                }
            }
        });
    }

    public interface OnRequestListener{
        public void onUpdate(ArrayList<CallDTO> calls);
        public void onNewCall(CallDTO call);
    }

}
