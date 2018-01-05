package com.company;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {



        Renderer rend = new Renderer();

        rend.start();
        rend.readMap();
        rend.createMap();
        while (true) {
            //Wait for a key to be pressed
            Key key;
            do {
                Thread.sleep(5);

                rend.draw();


                key = rend.terminal.readInput();
            }
            while (key == null);

            switch (key.getKind()) {
                case ArrowDown:
                    rend.updatePlayer(0,1);
                    rend.updateEnemy();
                    break;
                case ArrowUp:
                    rend.updatePlayer(0,-1);
                    break;
                case ArrowLeft:
                    rend.updatePlayer(-1,0);
                    break;
                case ArrowRight:
                    rend.updatePlayer(1,0);
                    break;
            }
            System.out.println(key.getCharacter() + " " + key.getKind());
        }
    }
}

