package com.example.alex.tic_tac_toev20;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Intent main;
    private int Players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.bt_withPlayer).setOnClickListener(this);
        findViewById(R.id.bt_withAndroid).setOnClickListener(this);
        findViewById(R.id.bt_about).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_withPlayer:
                Players = 2;
                main = new Intent(this, ChangeFieldActivity.class);
                main.putExtra("Players", Players);
                startActivity(main);
                break;
            case R.id.bt_withAndroid:
                Players = 1;
                main = new Intent(this, ChangeFieldActivity.class);
                main.putExtra("Players", Players);
                startActivity(main);
                break;
            case R.id.bt_about:
                main = new Intent(this, AboutGameActivity.class);
                startActivity(main);
                break;
        }

    }
}
