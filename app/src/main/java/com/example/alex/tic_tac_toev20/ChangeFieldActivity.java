package com.example.alex.tic_tac_toev20;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ChangeFieldActivity extends AppCompatActivity implements View.OnClickListener {
    Intent game;

    private int sizeField = 0;

    private EditText mFirstText;
    private EditText mSecondText;

    private ImageView mIvImageField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_field);
        int players = getIntent().getIntExtra("Players", 0);
        findView();
        setListner();
        if (players == 1) {
            mSecondText.setEnabled(false);
            mSecondText.setText(getString(R.string.player_android));
        }


    }

    private void setListner() {
        findViewById(R.id.bt_goToGame).setOnClickListener(this);
        findViewById(R.id.rb_field_3x3).setOnClickListener(this);
        findViewById(R.id.rb_field_4x4).setOnClickListener(this);
//        findViewById(R.id.rb_field_5x5).setOnClickListener(this);
//        findViewById(R.id.rb_field_6x6).setOnClickListener(this);
    }

    private void findView() {
        mFirstText = (EditText) findViewById(R.id.et_firstPlayer);
        mSecondText = (EditText) findViewById(R.id.et_secondPlayer);
        mIvImageField = (ImageView) findViewById(R.id.iv_imageField);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_goToGame:
                if (!mFirstText.getText().toString().equals("") && !mSecondText.getText().toString().equals("") && sizeField != 0) {
                    game = new Intent(this, GameFieldActivity.class);
                    game.putExtra("fullNameSecond", mSecondText.getText().toString());
                    game.putExtra("fullNameFirst", mFirstText.getText().toString());
                    game.putExtra("sizeField", sizeField);
                    startActivity(game);
                } else if (sizeField == 0) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_field), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_name), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rb_field_3x3:
                sizeField = 3;
                mIvImageField.setImageResource(R.drawable.field3x3);
                break;

            case R.id.rb_field_4x4:
                sizeField = 4;
                mIvImageField.setImageResource(R.drawable.field4x4);
                break;

//            case R.id.rb_field_5x5:
//                sizeField = 5;
//                break;
//
//            case R.id.rb_field_6x6:
//                sizeField = 6;
//                break;
        }

    }
}
