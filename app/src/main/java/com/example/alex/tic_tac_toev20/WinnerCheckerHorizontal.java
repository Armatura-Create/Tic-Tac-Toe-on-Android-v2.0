package com.example.alex.tic_tac_toev20;

class WinnerCheckerHorizontal implements WinnerCheckerInterface {;

    private Game game;

    WinnerCheckerHorizontal(Game game) {
        this.game = game;
    }

    public Player checkWinner() {
        Square[][] field = game.getField();
        Player currPlayer;
        Player lastPlayer = null;
        for (int i = 0, len = field.length; i < len; i++) {
            lastPlayer = null;
            int successCounter = 1;
            for (int j = 0, len2 = field[i].length; j < len2; j++) {
                currPlayer = field[i][j].getPlayer();
                if (currPlayer == lastPlayer && (currPlayer != null && lastPlayer !=null)) {
                    successCounter++;
                    if (successCounter == len2) {
                        return currPlayer;
                    }
                }
                lastPlayer = currPlayer;
            }
        }
        return null;
    }
}
