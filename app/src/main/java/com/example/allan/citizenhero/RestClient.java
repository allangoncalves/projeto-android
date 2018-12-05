package com.example.allan.citizenhero;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    private Context c;

    public void doPostCall(String json) throws IOException {

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

        Request request = new Request.Builder()
                .url(CALL_ENDPOINT)
                .post(body)
                .build();

        Call call = httpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d("post", "deu erro" + e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(response.isSuccessful()){
                    Log.d("post", "deu certo papai");
                }
            }
        });
    }

    public void doGetCalls(Long id) throws IOException {
        Request request = new Request.Builder().url(CALL_ENDPOINT + String.valueOf(id)).build();

        Call call = httpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(response.isSuccessful()){

                }
            }
        });
//        return response.body().toString();
    }

    public interface OnUpdateListView{
        public void onUpdate(CallDTO call);
    }


}
