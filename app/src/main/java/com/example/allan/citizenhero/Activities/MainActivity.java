package com.example.allan.citizenhero.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allan.citizenhero.Adapters.CallAdapter;
import com.example.allan.citizenhero.DTOs.CallDTO;
import com.example.allan.citizenhero.Dialogs.ExitDialog;
import com.example.allan.citizenhero.R;
import com.example.allan.citizenhero.RestClient;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ExitDialog.ExitListener, RestClient.OnRequestListener {

    ListView listView;

    TextView textView;

    boolean doublePressed = false;

    ImageView imageView;

    public static volatile List<CallDTO> calls = Collections.synchronizedList(new ArrayList<CallDTO>());

    CallAdapter adapter;

    RestClient httpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        Profile profile = Profile.getCurrentProfile();

        httpClient = new RestClient(this);

        this.imageView = (ImageView) findViewById(R.id.foto_fb);
        Picasso.with(this).load(profile.getProfilePictureUri(100, 100)).into(imageView);

        this.listView = (ListView) findViewById(R.id.list_ultimas);

        this.adapter = new CallAdapter(this, android.R.layout.simple_list_item_1, calls);

        try {
            httpClient.doGetCalls(Long.parseLong(profile.getId()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.listView.setAdapter(this.adapter);

        this.textView = findViewById(R.id.txt_nome_principal);
        this.textView.setText(profile.getName());



        FloatingActionButton fb = (FloatingActionButton) findViewById(R.id.fab_novo);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, FormActivity.class);
                startActivityForResult(it, 10);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CallDTO call = adapter.getItem(position);
                Log.d("post", call.getFullAddress());
                Intent it = new Intent(MainActivity.this, MapsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("call", call);
                it.putExtras(bundle);
                startActivity(it);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_logout:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                ExitDialog exitDialog = new ExitDialog();
                exitDialog.show(getSupportFragmentManager(), "exitDialog");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(doublePressed){
            moveTaskToBack(true);
            super.onBackPressed();
        }

        this.doublePressed = true;
        Toast.makeText(this, "Pressione novamente para fechar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doublePressed = false;
            }
        }, 2000);
    }

    @Override
    public void onExit() {
// REALIZAR LOGOUT DO FACEBOOK
        LoginManager.getInstance().logOut();
        Toast.makeText(this, "Logout do facebook necessario!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onUpdate(final ArrayList<CallDTO> calls) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.updateAll(calls);
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onNewCall(final CallDTO call) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.add(call);
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("post", requestCode + String.valueOf(resultCode));
        if (requestCode == 10){
            CallDTO call = (CallDTO) data.getExtras().getSerializable("call");
            Log.d("post", call.getFullAddress());
            onNewCall(call);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
