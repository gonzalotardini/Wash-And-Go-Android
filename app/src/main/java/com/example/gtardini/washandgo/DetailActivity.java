package com.example.gtardini.washandgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Intent intent = getIntent();
        String iddd = intent.getStringExtra("LAVADOID");


        setContentView(R.layout.activity_detail);
        TextView tv1 = (TextView)findViewById(R.id.textView);
        tv1.setText("HHHHHHHHHH" + iddd);
    }
}
