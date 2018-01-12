package com.company;

import java.util.Scanner;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Menu {

    Terminal terminal = TerminalFacade.createTerminal(System.in,
            System.out, Charset.forName("UTF8"));

    List<String> lines = new ArrayList<String>();
    char[][] menu = new char[24][70];

    public void Run() throws InterruptedException {
        Scanner scan = new Scanner(System.in);
        GameLogic logic = new GameLogic();

        terminal.enterPrivateMode();

        Key key;
        int input;
        do {

            drawMainMenu();
            //input = scan.nextInt();
            key = terminal.readInput();
            switch (key.getKind()) {
                case F1:
                    logic.gameLoop();
                    break;
                case F2:
                    // Look at high scores
                    break;
                case F3:
                    // Get help on how to move
                    break;
                case Escape:
                   System.exit(0);
                    break;
            }
        } while (key.getKind() != Key.Kind.Escape);

    }

    public void drawMainMenu() throws InterruptedException {


        int temp = 0;
        char c;

        try {
            lines = Files.readAllLines(Paths.get("src/MainMenu"), StandardCharsets.UTF_8);
            for (int i = 0; i < menu.length; i++) {
                for (int y = 0; y < 70; y++) {
                    menu[i][y] = lines.get(i).charAt(y);

                }
            }
            String s = lines.get(0);
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
        for (int i = 0; i < menu.length; i++) {
            for (int y = 0; y < 70; y++) {
                c = menu[i][y];
                terminal.moveCursor(y, i);
                terminal.putCharacter(c);
            }
        }

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
