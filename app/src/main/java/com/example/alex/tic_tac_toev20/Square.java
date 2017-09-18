package com.example.alex.tic_tac_toev20;

class Square {
    private Player player;

    void fill(Player player) {
        this.player = player;
    }

     boolean isFilled() {
         return player != null;
     }

    Player getPlayer() {
        return player;
    }
}
