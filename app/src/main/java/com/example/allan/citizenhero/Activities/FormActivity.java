package com.example.allan.citizenhero.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.allan.citizenhero.DTOs.CallDTO;
import com.example.allan.citizenhero.Preferences;
import com.example.allan.citizenhero.R;
import com.example.allan.citizenhero.RestClient;
import com.facebook.Profile;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

public class FormActivity extends AppCompatActivity implements RestClient.OnRequestListener {

    RestClient httpClient = new RestClient(this);

    private EditText edtStreet;
    private EditText edtCity;
    private EditText edtState;
    private EditText edtNeighborhood;
    private EditText edtObservation;
    private EditText edtComplement;
    private TextView txtAlert;
    private Spinner call_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        edtStreet = (EditText) findViewById(R.id.edt_rua);
        edtCity = (EditText) findViewById(R.id.edt_cidade);
        edtState = (EditText) findViewById(R.id.edt_estado);
        edtNeighborhood = (EditText) findViewById(R.id.edt_bairro);
        edtObservation = (EditText) findViewById(R.id.edt_obs);
        edtComplement = (EditText) findViewById(R.id.edt_complemento);
        txtAlert = (TextView) findViewById(R.id.txt_alert);
        call_type = (Spinner) findViewById(R.id.chosen_type);


        Gson gson = new Gson();

        SharedPreferences preferences = this.getSharedPreferences("formulario", 0);

        String json = preferences.getString("chamado", "");

        CallDTO call = gson.fromJson(json, CallDTO.class);

        if(call != null){
            if(call.getStreet() != null){
                edtStreet.setText(call.getStreet());
            }
            if(call.getNeighborhood() != null){
                edtNeighborhood.setText(call.getNeighborhood());
            }
            if(call.getCity() != null){
                edtCity.setText(call.getCity());
            }
            if(call.getState() != null){
                edtState.setText(call.getState());
            }
            if(call.getComplement() != null){
                edtComplement.setText(call.getComplement());
            }
        }


    }

    public void sendForm(View view) {
        if(isValidated()){
//            FAZER O POST PRA API
//            txtAlert.setVisibility(View.INVISIBLE);

            Profile profile = Profile.getCurrentProfile();

            CallDTO call = new CallDTO();
            call.setCallType(call_type.getSelectedItemPosition());
            call.setCity(getString(edtCity));
            call.setComplement(getString(edtComplement));
            call.setNeighborhood(getString(edtNeighborhood));
            call.setObservation(getString(edtObservation));
            call.setState(getString(edtState));
            call.setStreet(getString(edtStreet));
            call.setUserId(Long.parseLong(profile.getId()));

            try {
                httpClient.doPostCall(call);
            } catch (IOException e) {
                Log.d("post", "DA PRA PEGAR");
            }
            finally {
                Preferences.savePreferences(FormActivity.this, call);
            }
        }
        else{
            txtAlert.setVisibility(View.VISIBLE);
        }

    }

    public String getString(EditText edt){
        return edt.getText().toString();
    }

    protected boolean isValidated(){
        if(isEmpty(edtStreet) ||
                isEmpty(edtCity) ||
                isEmpty(edtState) ||
                isEmpty(edtNeighborhood) ||
                isEmpty(edtObservation) ||
                isEmpty(edtComplement)) {
            return false;
        }
        return true;
    }

    protected boolean isEmpty(EditText edtTxt){
        return edtTxt.getText().toString().isEmpty();
    }

    @Override
    public void onUpdate(ArrayList<CallDTO> calls) {

    }

    @Override
    public void onNewCall(CallDTO call) {

        Intent it = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();

        Log.d("post", "COE BID" + bundle.toString());
        Log.d("post", "BUNDLECALL" + call.toString());

        bundle.putSerializable("call", call);
        it.putExtras(bundle);
        setResult(RESULT_OK, it);
        finish();
    }
}
