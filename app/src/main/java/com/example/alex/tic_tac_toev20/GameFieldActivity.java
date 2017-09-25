package com.example.alex.tic_tac_toev20;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.alex.tic_tac_toev20.R.string.draw;

public class GameFieldActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTvScoreTextFirstPlayer;
    private TextView mTvScoreTextSecondPlayer;

    private Button[][] buttons;
    private Game game;
    private TableLayout layout;

    private String fullNameFirst;
    private String fullNameSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_field);
        fullNameFirst = getIntent().getStringExtra("fullNameFirst");
        fullNameSecond = getIntent().getStringExtra("fullNameSecond");
        game = new Game(getIntent().getIntExtra("sizeField", 3));
        game.start();
        buttons = new Button[getIntent().getIntExtra("sizeField", 3)][getIntent().getIntExtra("sizeField", 3)];
        layout = (TableLayout) findViewById(R.id.main_l);
        findViewById(R.id.bt_mainMenu_field).setOnClickListener(this);
        findViewById(R.id.bt_newGame).setOnClickListener(this);
        buildGameField();

    }

    public void buildGameField() {
        Square[][] field = game.getField();
        for (int i = 0; i < field.length; i++) {
            TableRow row = new TableRow(this); // создание строки таблицы
            for (int j = 0; j < field[i].length; j++) {
                Button button = new Button(this);
                buttons[i][j] = button;
                button.setOnClickListener(new Listener(i, j));
                button.setWidth(200);
                button.setHeight(200);
                row.addView(button, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

            }
            layout.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
        }
        mTvScoreTextFirstPlayer = (TextView) findViewById(R.id.tv_playerOne);
        mTvScoreTextSecondPlayer = (TextView) findViewById(R.id.tv_playerTwo);
        mTvScoreTextFirstPlayer.setText(fullNameFirst + " \"X\": " + game.scoreFirstPlayer);
        mTvScoreTextSecondPlayer.setText(fullNameSecond + " \"O\": " + game.scoreFirstPlayer);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_mainMenu_field:
                Intent intent = new Intent(GameFieldActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_newGame:
                game.reset();
                refresh();
                break;
        }

    }

    private class Listener implements View.OnClickListener {
        private int x = 0;
        private int y = 0;

        Listener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void onClick(View view) {
            Button button = (Button) view;
            Game g = game;
            Player player = g.getCurrentActivePlayer();
            if (game.makeTurn(x, y)) {
                button.setText(player.getName());
            }
            Player winner = g.checkWinner();
            if (winner != null) {
                gameOver(winner);
            }
            if (g.isFieldFilled()) {  // в случае, если поле заполнено
                gameOver();
            }
        }
    }

    private void gameOver(Player player) {
        score(player);
        String text = "Player \"" + player.getName() + "\" won!";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        notClicable();
    }

    private void gameOver() {
        Toast.makeText(this, draw, Toast.LENGTH_SHORT).show();
        notClicable();
    }

    private void notClicable() {
        Square[][] field = game.getField();

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                buttons[i][j].setClickable(false);
            }
        }
    }

    private void score(Player player) {
        if (player.getName() == "X") {
            game.scoreFirstPlayer++;
            mTvScoreTextFirstPlayer.setText(fullNameFirst + " \"" + player.getName() + "\": " + game.scoreFirstPlayer);
        } else {
            game.scoreSecondPlayer++;
            mTvScoreTextSecondPlayer.setText(fullNameSecond + " \"" + player.getName() + "\": " + game.scoreSecondPlayer);
        }
    }

    private void refresh() {
        Square[][] field = game.getField();

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j].getPlayer() == null) {
                    buttons[i][j].setText("");
                } else {
                    buttons[i][j].setText(field[i][j].getPlayer().getName());
                }
            }
        }
    }
}