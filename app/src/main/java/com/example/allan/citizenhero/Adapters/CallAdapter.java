package com.example.allan.citizenhero.Adapters;


import com.example.allan.citizenhero.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.allan.citizenhero.DTOs.CallDTO;

import org.w3c.dom.Text;

import java.util.Collection;
import java.util.List;

public class CallAdapter extends ArrayAdapter<CallDTO> {

    private List<CallDTO> calls;
    private Context context;

    public CallAdapter(Context context, int resource, List<CallDTO> calls) {
        super(context, resource, calls);
        this.context = context;
        this.calls = calls;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        ItemChamado itemHolder;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.chamado_item, null);
            itemHolder = new ItemChamado();
            itemHolder.textView = (TextView) convertView.findViewById(R.id.endereco_completo_lista);
            convertView.setTag(itemHolder);
        }
        else{
            itemHolder = (ItemChamado) convertView.getTag();
        }

        CallDTO call = calls.get(position);
        itemHolder.textView.setText(call.getFullAddress());


        return convertView;
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

    private class ItemChamado{
        TextView textView;
    }
}
