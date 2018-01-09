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

                rend.draw();

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
                    rend.updatePlayer(0,1);
                   // rend.updateEnemy((rand.nextInt(2 + 1) - 1), (rand.nextInt(2 + 1) - 1));
                    break;
                case ArrowUp:
                    rend.updatePlayer(0,-1);
                   // rend.updateEnemy((rand.nextInt(2 + 1) - 1), (rand.nextInt(2 + 1) - 1));
                    break;
                case ArrowLeft:
                    rend.updatePlayer(-1,0);
                   // rend.updateEnemy((rand.nextInt(2 + 1) - 1), (rand.nextInt(2 + 1) - 1));
                    break;
                case ArrowRight:
                    rend.updatePlayer(1,0);
                 //   rend.updateEnemy((rand.nextInt(2 + 1) - 1), (rand.nextInt(2 + 1) - 1));
                    break;
                case Enter:
//                    rend.meleeAttack();
                    break;
                case Escape:
                    System.exit(0);

            }

            System.out.println(key.getCharacter() + " " + key.getKind());
        }
    }
}

