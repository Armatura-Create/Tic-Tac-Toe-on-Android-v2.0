package com.example.alex.tic_tac_toev20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public int sizeField = 5;
    private Button[][] buttons = new Button[sizeField][sizeField];
    private Game game;
    private TableLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);layout = (TableLayout) findViewById(R.id.main_l);
        buildGameField();
    }

    public MainActivity() {
        game = new Game();
        game.start();
    }

    public void buildGameField() {
        Square[][] field = game.getField();
        for (int i = 0; i < field.length; i++) {
            TableRow row = new TableRow(this); // создание строки таблицы
            for (int j = 0; j < field[i].length; j++) {
                Button button = new Button(this);
                buttons[i][j] = button;
                button.setOnClickListener(new Listener(i, j));
                row.addView(button, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                button.setWidth(200);
                button.setHeight(200);
            }
            layout.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
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
        CharSequence text = "Player \"" + player.getName() + "\" won!";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        game.reset();
        refresh();
    }

    private void gameOver() {
        CharSequence text = "Draw";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        game.reset();
        refresh();
    }

    private void refresh() {
        Square[][] field = game.getField();

        for (int i = 0, len = field.length; i < len; i++) {
            for (int j = 0, len2 = field[i].length; j < len2; j++) {
                if (field[i][j].getPlayer() == null) {
                    buttons[i][j].setText("");
                } else {
                    buttons[i][j].setText(field[i][j].getPlayer().getName());
                }
            }
        }
    }
}