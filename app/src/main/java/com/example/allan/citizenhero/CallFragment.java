package com.example.allan.citizenhero;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import com.facebook.Profile;

import java.io.IOException;

public class CallFragment extends ListFragment {

    private ArrayAdapter<CallDTO> adapter;
    private OnItemClick listener;
    private RestClient httpClient = new RestClient();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(!(context instanceof OnItemClick)){
            throw new RuntimeException("Deve ser OnItemClick!");
        }

        listener = (OnItemClick) context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1);

        Long id = Long.parseLong(Profile.getCurrentProfile().getId().toString());

        try {
            httpClient.doGetCalls(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface OnItemClick{
        void onClick(CallDTO call);
    }
}
