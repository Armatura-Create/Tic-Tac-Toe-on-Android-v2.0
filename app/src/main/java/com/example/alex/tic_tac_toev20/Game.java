package com.example.alex.tic_tac_toev20;


class Game {
    /**
     * Счет игроков
     */
    int scoreFirstPlayer;
    int scoreSecondPlayer;
    /**
     * игроки
     */
    private Player[] players;
    /**
     * поле
     */
    private Square[][] field;
    /**
     * текущий игрок
     */
    private Player activePlayer;
    /**
     * Считает колличество заполненных ячеек
     */
    private int filled;
    /**
     * Всего ячеек
     */
    private int squareCount;

    /**
     * Проверка на победную комбинацию
     */
    private WinnerCheckerInterface[] winnerCheckers;

    Game(int sizeField) {
        field = new Square[sizeField][sizeField];
        squareCount = 0;
        scoreFirstPlayer = 0;
        scoreSecondPlayer = 0;

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = new Square();
                squareCount++;
            }
        }
        players = new Player[2];
        activePlayer = null;
        filled = 0;

        winnerCheckers = new WinnerCheckerInterface[4];
        winnerCheckers[0] = new WinnerCheckerHorizontal(this);
        winnerCheckers[1] = new WinnerCheckerVertical(this);
        winnerCheckers[2] = new WinnerCheckerDiagonalLeft(this);
        winnerCheckers[3] = new WinnerCheckerDiagonalRight(this);
    }

    void start() {
        resetPlayers();
    }

    private void resetPlayers() {
        players[0] = new Player("X");
        players[1] = new Player("O");
        setCurrentActivePlayer(players[0]);
    }

    Square[][] getField() {
        return field;
    }

    private void setCurrentActivePlayer(Player player) {
        activePlayer = player;
    }

    boolean makeTurn(int x, int y) {
        if (field[x][y].isFilled()) {
            return false;
        }
        field[x][y].fill(getCurrentActivePlayer());
        filled++;
        switchPlayers();
        return true;
    }

    private void switchPlayers() {
        activePlayer = (activePlayer == players[0]) ? players[1] : players[0];
    }

    Player getCurrentActivePlayer() {
        return activePlayer;
    }

    boolean isFieldFilled() {
        return squareCount == filled;
    }

    void reset() {
        resetField();
        resetPlayers();
    }

    private void resetField() {
        for (Square[] aField : field) {
            for (Square anAField : aField) {
                anAField.fill(null);
            }
        }
        filled = 0;
    }

    Player checkWinner() {
        for (WinnerCheckerInterface winChecker : winnerCheckers) {
            Player winner = winChecker.checkWinner();
            if (winner != null) {
                return winner;
            }
        }
        return null;
    }
    public Player getPlayerFirst(){
        return players[0];
    }

    public Player getPlayerSecond(){
        return players[1];
    }

}