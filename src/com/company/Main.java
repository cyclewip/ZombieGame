package com.company;

import com.googlecode.lanterna.input.Key;

import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Random rand = new Random();

        Renderer rend = new Renderer();

        rend.start();

        while (true) {
            //Wait for a key to be pressed
            Key key;
            do {
                Thread.sleep(50);

                rend.readMap();
                rend.createMap();
                rend.renderScores();
                rend.draw();
                rend.renderStuff();


                key = rend.terminal.readInput();
            }
            while (key == null);

            switch (key.getKind()) {
                case ArrowDown:
//                    rend.player.setKey(key);
                    rend.updatePlayer(0, 1);
//                    rend.updateEnemy();
                    break;
                case ArrowUp:
                    rend.updatePlayer(0, -1);
//                    rend.updateEnemy();
                    break;
                case ArrowLeft:
                    rend.updatePlayer(-1, 0);
//                    rend.updateEnemy();
                    break;
                case ArrowRight:
                    rend.updatePlayer(1, 0);
//                    rend.updateEnemy();
                    break;
                case Enter:
                    rend.enterPressed = true;
                    break;
                case Escape:
                    System.exit(0);

            }

            System.out.println(key.getCharacter() + " " + key.getKind());
        }
    }
}

