package com.example.allan.citizenhero;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.Profile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import org.w3c.dom.Text;

import java.io.IOException;

public class FormActivity extends AppCompatActivity {

    private Gson gson = new GsonBuilder().create();

    RestClient httpClient = new RestClient();


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

            String json_obj = gson.toJson(call);
            Log.d("perf", "coe");
            try {
                httpClient.doPostCall(json_obj);
            } catch (IOException e) {
                e.printStackTrace();
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
}
