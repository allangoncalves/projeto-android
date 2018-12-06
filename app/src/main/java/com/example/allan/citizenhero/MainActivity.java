package com.example.allan.citizenhero;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Handler;
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

import com.facebook.Profile;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ExitDialog.ExitListener, RestClient.OnGetCallsListener {

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

        Log.d("get", profile.getId());

        httpClient = new RestClient(this);

        this.imageView = (ImageView) findViewById(R.id.foto_fb);
        Picasso.with(this).load(profile.getProfilePictureUri(100, 100)).into(imageView);

        this.listView = (ListView) findViewById(R.id.list_ultimas);

        this.adapter = new CallAdapter(this, android.R.layout.simple_list_item_1, calls);

        this.listView.setAdapter(this.adapter);

        try {
            httpClient.doGetCalls(Long.parseLong(profile.getId()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.textView = findViewById(R.id.txt_nome_principal);
        this.textView.setText(profile.getName());



        FloatingActionButton fb = (FloatingActionButton) findViewById(R.id.fab_novo);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, FormActivity.class);
                startActivity(it);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CallDTO call = adapter.getItem(position);
                Log.d("get", "POXA KRA KD O MAPA" + call.getFullAddress());
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
        Toast.makeText(this, "Logout do facebook necessario!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdate(ArrayList<CallDTO> calls) {
        adapter.updateAll(calls);
    }

    @Override
    public void onNewCall(CallDTO call) {
        adapter.add(call);
    }
}
