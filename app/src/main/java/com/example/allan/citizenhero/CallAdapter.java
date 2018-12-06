package com.example.allan.citizenhero;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CallAdapter extends ArrayAdapter<CallDTO> {

    private List<CallDTO> calls;

    public CallAdapter(Context context, int resource, List<CallDTO> calls) {
        super(context, resource, calls);
        this.calls = calls;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    public void updateAll(Collection<? extends  CallDTO> callsFromApi){
        calls.clear();
        calls.addAll(callsFromApi);
//        notifyDataSetChanged();
    }

    public void add(CallDTO call){
        calls.add(call);
//        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return calls.indexOf(calls.get(position));
    }
}
