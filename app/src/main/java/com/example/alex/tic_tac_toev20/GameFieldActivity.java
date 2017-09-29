package com.example.alex.tic_tac_toev20;

import android.content.Intent;
import android.os.Build;
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
    private TextView mTvAllGames;
    private TextView mTvAllDraws;

    public static Button[][] mButtons;
    private Square[][] mField;

    private Game mGame;
    private TableLayout mLayout;

    private String fullNameFirst;
    private String fullNameSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_field);
        fullNameFirst = getIntent().getStringExtra("fullNameFirst");
        fullNameSecond = getIntent().getStringExtra("fullNameSecond");
        mLayout = (TableLayout) findViewById(R.id.main_l);
        mButtons = new Button[getIntent().getIntExtra("sizeField", 3)][getIntent().getIntExtra("sizeField", 3)];

        mTvAllGames = (TextView) findViewById(R.id.tv_all_games);
        mTvAllDraws = (TextView) findViewById(R.id.tv_all_draws);
        mTvScoreTextFirstPlayer = (TextView) findViewById(R.id.tv_playerOne);
        mTvScoreTextSecondPlayer = (TextView) findViewById(R.id.tv_playerTwo);

        buildGameField();
        mGame.start();

        findViewById(R.id.bt_mainMenu_field).setOnClickListener(this);
        findViewById(R.id.bt_newGame).setOnClickListener(this);
    }

    public void buildGameField() {
        mGame = new Game(getIntent().getIntExtra("sizeField", 3), mButtons);
        mField = mGame.getField();
        for (int i = 0; i < mField.length; i++) {
            TableRow row = new TableRow(this); // создание строки таблицы
            for (int j = 0; j < mField[i].length; j++) {
                Button button = new Button(this);
                mButtons[i][j] = button;
                button.setOnClickListener(new Listener(i, j));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    button.setBackground(getDrawable(R.drawable.background_bt_st));
                }
                button.setTextSize(36);
                button.setWidth(200);
                button.setHeight(200);
                row.addView(button, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

            }
            mLayout.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
        }

        mTvScoreTextFirstPlayer.setText(fullNameFirst + " \"X\": " + mGame.scoreFirstPlayer);
        mTvScoreTextSecondPlayer.setText(fullNameSecond + " \"O\": " + mGame.scoreFirstPlayer);

        mTvAllGames.setText(getString(R.string.all_games) + ": " + mGame.scoreAllGames);
        mTvAllDraws.setText(getString(R.string.all_draws) + ": " + mGame.scoreAllDraws);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_mainMenu_field:
                Intent intent = new Intent(GameFieldActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_newGame:
                mGame.reset();
                refresh();
                trueClickable();
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
            Player player = mGame.getCurrentActivePlayer();
            if (mGame.makeTurn(x, y)) {
                button.setText(player.getName());
            }
            Player winner = mGame.checkWinner();
            if (winner != null) {
                gameOver(winner);
            } else if (mGame.isFieldFilled()) {  // в случае, если поле заполнено
                gameOver();
            }
        }
    }

    private void gameOver(Player player) {
        paintWinner();
        mGame.scoreAllGames++;
        score();
        score(player);
        String text = "Player \"" + player.getName() + "\" won!";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        falseClickable();
    }

    private void gameOver() {
        mGame.scoreAllGames++;
        mGame.scoreAllDraws++;
        score();
        Toast.makeText(this, draw, Toast.LENGTH_SHORT).show();
        falseClickable();
    }

    private void falseClickable() {
        for (int i = 0; i < mField.length; i++) {
            for (int j = 0; j < mField[i].length; j++) {
                mButtons[i][j].setClickable(false);
            }
        }
    }

    private void trueClickable() {
        for (int i = 0; i < mField.length; i++) {
            for (int j = 0; j < mField[i].length; j++) {
                mButtons[i][j].setClickable(true);
            }
        }
    }

    private void score(Player player) {
        if (player.getName() == "X") {
            mGame.scoreFirstPlayer++;
            mTvScoreTextFirstPlayer.setText(fullNameFirst + " \"" + player.getName() + "\": " + mGame.scoreFirstPlayer);
        } else {
            mGame.scoreSecondPlayer++;
            mTvScoreTextSecondPlayer.setText(fullNameSecond + " \"" + player.getName() + "\": " + mGame.scoreSecondPlayer);
        }
    }

    private void score() {
        mTvAllGames.setText(getString(R.string.all_games) + ": " + mGame.scoreAllGames);
        mTvAllDraws.setText(getString(R.string.all_draws) + ": " + mGame.scoreAllDraws);
    }

    private void refresh() {
        for (int i = 0; i < mField.length; i++) {
            for (int j = 0; j < mField[i].length; j++) {
                if (mField[i][j].getPlayer() == null) {
                    mButtons[i][j].setText("");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mButtons[i][j].setBackground(getDrawable(R.drawable.background_bt_st));
                    }
                } else {
                    mButtons[i][j].setText(mField[i][j].getPlayer().getName());
                }
            }
        }
    }

    private void paintWinner() {
        Player currPlayer;
        Player lastPlayer = null;
        Player preLastPlayer = null;
        Player preLastPlayer2 = null;
        for (int i = 0, len = mField.length; i < len; i++) {
            for (int j = 0; j < mField[i].length; j++) {
                currPlayer = mField[i][j].getPlayer();
                if (mField.length == 4) {
                    if (currPlayer != null) {
                        if (lastPlayer == currPlayer && currPlayer == preLastPlayer && preLastPlayer == preLastPlayer2)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                mButtons[i][j - 3].setBackground(getDrawable(R.drawable.background_bt_win));
                                mButtons[i][j - 2].setBackground(getDrawable(R.drawable.background_bt_win));
                                mButtons[i][j - 1].setBackground(getDrawable(R.drawable.background_bt_win));
                                mButtons[i][j].setBackground(getDrawable(R.drawable.background_bt_win));
                            }
                    }
                } else {
                    if (currPlayer != null) {
                        if (lastPlayer == currPlayer && currPlayer == preLastPlayer) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                mButtons[i][j - 2].setBackground(getDrawable(R.drawable.background_bt_win));
                                mButtons[i][j - 1].setBackground(getDrawable(R.drawable.background_bt_win));
                                mButtons[i][j].setBackground(getDrawable(R.drawable.background_bt_win));
                            }
                        }
                    }
                }
                if (mField.length == 4) {
                    preLastPlayer2 = preLastPlayer;
                }
                preLastPlayer = lastPlayer;
                lastPlayer = currPlayer;
            }
        }
        lastPlayer = null;
        preLastPlayer = null;
        preLastPlayer2 = null;
        for (int i = 0, len = mField.length; i < len; i++) {
            for (int j = 0; j < mField[i].length; j++) {
                currPlayer = mField[j][i].getPlayer();
                if (mField.length == 4) {
                    if (currPlayer != null) {
                        if (lastPlayer == currPlayer && currPlayer == preLastPlayer && preLastPlayer == preLastPlayer2)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                mButtons[j - 3][i].setBackground(getDrawable(R.drawable.background_bt_win));
                                mButtons[j - 2][i].setBackground(getDrawable(R.drawable.background_bt_win));
                                mButtons[j - 1][i].setBackground(getDrawable(R.drawable.background_bt_win));
                                mButtons[j][i].setBackground(getDrawable(R.drawable.background_bt_win));
                            }
                    }
                } else {
                    if (currPlayer != null) {
                        if (lastPlayer == currPlayer && currPlayer == preLastPlayer) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                mButtons[j - 2][i].setBackground(getDrawable(R.drawable.background_bt_win));
                                mButtons[j - 1][i].setBackground(getDrawable(R.drawable.background_bt_win));
                                mButtons[j][i].setBackground(getDrawable(R.drawable.background_bt_win));
                            }
                        }
                    }
                }
                if (mField.length == 4) {
                    preLastPlayer2 = preLastPlayer;
                }
                preLastPlayer = lastPlayer;
                lastPlayer = currPlayer;
            }
        }
        lastPlayer = null;
        preLastPlayer = null;
        preLastPlayer2 = null;
        for (int i = 0, len = mField.length; i < len; i++) {
            currPlayer = mField[i][i].getPlayer();
            if (mField.length == 4) {
                if (currPlayer != null) {
                    if (lastPlayer == currPlayer && currPlayer == preLastPlayer && preLastPlayer == preLastPlayer2)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mButtons[i][i].setBackground(getDrawable(R.drawable.background_bt_win));
                            mButtons[i - 1][i - 1].setBackground(getDrawable(R.drawable.background_bt_win));
                            mButtons[i - 2][i - 2].setBackground(getDrawable(R.drawable.background_bt_win));
                            mButtons[i - 3][i - 3].setBackground(getDrawable(R.drawable.background_bt_win));
                        }
                }
            } else {
                if (currPlayer != null) {
                    if (lastPlayer == currPlayer && currPlayer == preLastPlayer) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mButtons[i][i].setBackground(getDrawable(R.drawable.background_bt_win));
                            mButtons[i - 1][i - 1].setBackground(getDrawable(R.drawable.background_bt_win));
                            mButtons[i - 2][i - 2].setBackground(getDrawable(R.drawable.background_bt_win));
                        }
                    }
                }
            }
            if (mField.length == 4) {
                preLastPlayer2 = preLastPlayer;
            }
            preLastPlayer = lastPlayer;
            lastPlayer = currPlayer;
        }
        lastPlayer = null;
        preLastPlayer = null;
        preLastPlayer2 = null;
        for (int i = 0; i < mField.length; i++) {
            currPlayer = mField[i][mField.length - (i + 1)].getPlayer();
            if (mField.length == 4) {
                if (currPlayer != null) {
                    if (lastPlayer == currPlayer && currPlayer == preLastPlayer && preLastPlayer == preLastPlayer2)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mButtons[i][i - 3].setBackground(getDrawable(R.drawable.background_bt_win));
                            mButtons[i - 1][i - 2].setBackground(getDrawable(R.drawable.background_bt_win));
                            mButtons[i - 2][i - 1].setBackground(getDrawable(R.drawable.background_bt_win));
                            mButtons[i - 3][i].setBackground(getDrawable(R.drawable.background_bt_win));
                        }
                }
            } else {
                if (currPlayer != null) {
                    if (lastPlayer == currPlayer && currPlayer == preLastPlayer) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mButtons[i][i - 2].setBackground(getDrawable(R.drawable.background_bt_win));
                            mButtons[i - 1][i - 1].setBackground(getDrawable(R.drawable.background_bt_win));
                            mButtons[i - 2][i].setBackground(getDrawable(R.drawable.background_bt_win));
                        }
                    }
                }
            }
            if (mField.length == 4) {
                preLastPlayer2 = preLastPlayer;
            }
            preLastPlayer = lastPlayer;
            lastPlayer = currPlayer;
        }
    }
}