package com.company;

public class Menu {

    public void Run() {

        int playGame = 0;

        do {
            switch (playGame) {
                case 1:
                    // Draw game board and start game
                    break;
                case 2:
                    // Look at high scores
                    break;
                case 3:
                    // Get help on how to move
                    break;
                case 4:
                    // Exit game using terminal.exit(0);
                    break;
                default:
                    //Handle in case some one presses the wrong numbers?
            }
        } while (playGame != 3);

    }

    public void startGame() {

    }

    public void drawHighScoreMenu() {

    }

    public void drawGameOverMenu() {

    }

    public void quitGame() {

    }

}
