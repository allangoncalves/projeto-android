package com.example.allan.citizenhero;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements RestClient.OnUpdateListView {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_ultimas);

        FloatingActionButton fb = (FloatingActionButton) findViewById(R.id.fab_novo);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, FormActivity.class);
                startActivity(it);
            }
        });
    }

    @Override
    public void onUpdate(CallDTO call) {

    }
}
