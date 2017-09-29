package com.example.alex.tic_tac_toev20;

class WinnerCheckerDiagonalRight implements WinnerCheckerInterface {
    private Game game;

    WinnerCheckerDiagonalRight(Game game) {
        this.game = game;
    }

    public Player checkWinner() {
        Square[][] field = game.getField();
        Player currPlayer;
        Player lastPlayer = null;
        int successCounter = 1;
        for (int i = 0;i < field.length; i++) {
            currPlayer = field[i][field.length - (i + 1)].getPlayer();
            if (currPlayer != null) {
                if (lastPlayer == currPlayer) {
                    successCounter++;
                    if (successCounter == field.length) {
                        return currPlayer;
                    }
                }
            }
            lastPlayer = currPlayer;
        }
        return null;
    }
}
