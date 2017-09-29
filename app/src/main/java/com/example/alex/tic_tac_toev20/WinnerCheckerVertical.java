package com.example.alex.tic_tac_toev20;

class WinnerCheckerVertical implements WinnerCheckerInterface {
    private Game game;

    WinnerCheckerVertical(Game game) {
        this.game = game;
    }

    public Player checkWinner() {
        Square[][] field = game.getField();
        Player currPlayer;
        Player lastPlayer;
        for (int i = 0; i < field.length; i++) {
            lastPlayer = null;
            int successCounter = 1;
            for (int j = 0; j < field[i].length; j++) {
                currPlayer = field[j][i].getPlayer();
                if (currPlayer == lastPlayer && currPlayer != null) {
                    successCounter++;
                    if (successCounter == field.length) {
                        return currPlayer;
                    }
                }
                lastPlayer = currPlayer;
            }
        }
        return null;
    }
}
