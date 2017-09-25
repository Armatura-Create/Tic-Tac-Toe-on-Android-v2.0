package com.example.alex.tic_tac_toev20;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AboutGameActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_game);
        findViewById(R.id.bt_main_about).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(AboutGameActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
