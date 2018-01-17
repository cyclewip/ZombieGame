package com.company;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;
import javafx.scene.shape.Arc;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

//import com.googlecode.lanterna.graphics.Styleset;
//import com.googlecode.lanterna.graphics.TextGraphics;
//import com.googlecode.lanterna.TextColor.ANSI;

public class Renderer {

    Terminal terminal = TerminalFacade.createTerminal(System.in,
            System.out, Charset.forName("UTF8"));
    List<String> lines = new ArrayList<String>();
    List<Archetype> allEnemies = new ArrayList<Archetype>();
    List<Powerup> allPowerups = new ArrayList<Powerup>();
    List<Bullet> bulletList = new ArrayList<Bullet>();

    boolean collided = false;
    boolean isAWall = false;
    boolean attack = false;
    boolean enterPressed = false;
    boolean enterF1 = false;
    boolean secondPassed = false;
    int meleeAttackCounter = 0;
    int enemiesInrange = 0;

    char[][] map = new char[24][70];
    char[][] mapMainMenu = new char[24][70];

    Random rand = new Random();

    List<Integer> ints = new ArrayList<Integer>();


    static int interval = 0;


    Player player = new Player(11, 10);
    Archetype archetype;

//    Powerup healthPowerUp = new HealthPowerUp(10, 10);
//    Powerup scorePowerUp = new ScorePowerUp(11, 11);
//    Powerup damagePowerUp = new DamagePowerUp(10, 12);

    public Renderer() {
    }

    int secondsPassed = 0;
    int enemyTimer = 0;
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            updateEnemy();
//            enemySpawner();
            enemyTimer++;
            if (enemyTimer >= 15)
                enemyTimer = 0;
            secondsPassed++;
            enemyTimer++;
//            System.out.println("enemytimer" + enemyTimer);
        }
    };
    Timer timer2 = new Timer();
    TimerTask task2 = new TimerTask() { // TIMER FOR ATTACKS OF ENEMIES
        @Override
        public void run() {
//            System.out.println("attacked");
            if (player.getHitPoints() > 0 && enemiesInrange > 0)
                player.setHitPoints(player.getHitPoints() - enemiesInrange * 5);
        }
    };
    int powerUpTimer = 0;
    Timer timer3 = new Timer();
    TimerTask task3 = new TimerTask() { // TIMER FOR ATTACKS OF ENEMIES
        @Override
        public void run() {
            powerUpSpawner();
        }
    };
    Timer timer4 = new Timer();
    TimerTask task4 = new TimerTask() { // TIMER FOR ATTACKS OF ENEMIES
        @Override
        public void run() {
            updateBullet();
        }
    };

    public void start() {
//        terminal.enterPrivateMode();
//        allEnemies.add(new SmartEnemy(rand.nextInt(10) + 4, rand.nextInt(10) + 5, "Smart"));
//        allEnemies.add(new StupidEnemy(rand.nextInt(10) + 5, rand.nextInt(10) + 5, "Stupid"));
        allEnemies.add(new StupidEnemy(13, 13, "Smart"));
        allEnemies.add(new StupidEnemy(13, 20, "Smart"));
        allEnemies.add(new StupidEnemy(13, 21, "Smart"));
        allEnemies.add(new StupidEnemy(13, 22, "Smart"));
//        allEnemies.add(new StupidEnemy(13, 23, "Smart"));

//        allEnemies.add(new StupidEnemy(1, 11, "Stupid"));
//        allEnemies.add(new StupidEnemy(1, 12, "Stupid"));
//        allEnemies.add(new StupidEnemy(1, 13, "Stupid"));
//        allEnemies.add(new StupidEnemy(1, 14, "Stupid"));
//        allEnemies.add(new StupidEnemy(1, 15, "Stupid"));

//        allEnemies.add(new StupidEnemy(rand.nextInt(10) + 5, rand.nextInt(10) + 5, "Stupid"));

        allPowerups.add(new HealthPowerUp(10, 10));
        allPowerups.add(new ScorePowerUp(11, 11));
        allPowerups.add(new DamagePowerUp(12, 12));
    }

    public void scheduleTime() {
        timer.scheduleAtFixedRate(task, 500, 300);
        timer2.scheduleAtFixedRate(task2, 500, 750);
        timer3.scheduleAtFixedRate(task3, 500, 5000);
        timer4.scheduleAtFixedRate(task4, 200, 75);
    }

    public void readMap() {
        try {
            lines = Files.readAllLines(Paths.get("src/map.txt"), StandardCharsets.UTF_8);
            for (int i = 0; i < map.length; i++) {
                for (int y = 0; y < 70; y++) {
                    map[i][y] = lines.get(i).charAt(y);
                }
            }
            String s = lines.get(0);
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    public void createMap() {
        int temp = 0;
        char c;
        for (int i = 0; i < map.length; i++) {
            for (int y = 0; y < 70; y++) {
                c = map[i][y];
                terminal.moveCursor(y, i);
                terminal.putCharacter(c);
            }
        }
    }

    public void resetGame() {
        player.setHitPoints(100);
        player.setHighScore(0);
        player.setPowerUpDamage(2);
        player.setPowerUpHighScore(5);
        player.setWon(false);
        player.setLost(false);
        player.setX(12);
        player.setY(12);
        ////// REMOVES ALL OBJECTS IN THE LISTS ////////
        for (Iterator<Archetype> iter = allEnemies.listIterator(); iter.hasNext(); ) {
            Archetype a = iter.next();
            iter.remove();
        }
        for (Iterator<Powerup> iter = allPowerups.listIterator(); iter.hasNext(); ) {
            Powerup a = iter.next();
            iter.remove();
        }
        for (Iterator<Bullet> iter = bulletList.listIterator(); iter.hasNext(); ) {
            Bullet a = iter.next();
            iter.remove();
        }
    }

    public void powerUpSpawner() {
        int randX = rand.nextInt(35) + 25;
        int randY = rand.nextInt(10) + 8;
        int r = rand.nextInt(3) + 1;
        int maxPowerups = 0;
        for (int i = 0; i < allPowerups.size(); i++) {
            if (allPowerups.get(i).isPickedUp())
                maxPowerups++;
        }
        if (maxPowerups <= 3) {
            if (r == 1) {
                allPowerups.add(new DamagePowerUp(randX, randY));
            } else if (r == 2) {
                allPowerups.add(new HealthPowerUp(randX, randY));
            } else {
                allPowerups.add(new HealthPowerUp(randX, randY));
            }
        }
    }

    public void enemySpawner() {
        int randX = rand.nextInt(35) + 25;
        int randY = rand.nextInt(10) + 8;
        int r = rand.nextInt(2) + 1;
        int maxEnemies = 0;
        for (int i = 0; i < allEnemies.size(); i++) {
            if (allEnemies.get(i).isAlive)
                maxEnemies++;
        }

        if (enemyTimer == 5 && maxEnemies <= 10) {
            if (r == 2) {
                allEnemies.add(new SmartEnemy(randX, randY, "Smart"));
            } else {
                allEnemies.add(new StupidEnemy(randX, randY, "Stupid"));
            }
        }
    }

    ///////////// IS USED IN bullet() ///// STORES VALUES from bulletvalues(), to use in next bullet
    public String bulletDir = "";
    int directionX = 0;
    int directionY = 0;
    String direct = "";

    ///////////// IS USED IN bullet() ///// STORES VALUES from bulletvalues(), to use in next bullet
    public void bulletValues(int tempDirX, int tempDirY, String tempdirect) {
        directionX = tempDirX;
        directionY = tempDirY;
        direct = tempdirect;
    }

    public void bullet() {
        int bulletsAlive = 0;
        for (int i = 0; i < bulletList.size(); i++) {
            if (bulletList.get(i).isalive)
                bulletsAlive++;
            ///////// IF DIRECTION IS 2, set it to 1, so we can use it to look at next movement towards enemy
            if (directionX > 1)
                bulletList.get(i).setTempPosX(1);   // SET NEXT POS FOR BULLET to + 1 or -1, instead of 2 or -2.
            else if (directionX < 0)             // 2 IS SO ThE BULLET FLIES OUT 1 STEP AHEAD OF THE PLAYER
                bulletList.get(i).setTempPosX(-1);
            if (directionY > 1)
                bulletList.get(i).setTempPosY(1);
            else if (directionY < 0)
                bulletList.get(i).setTempPosY(-1);
        }
        direct.contains(bulletDir);
        int maxBullets = 5;
        if (inBounds(player.getX() + directionX, player.getY() + directionY) && enterF1 && bulletsAlive < maxBullets) {
            bulletList.add(new Bullet(player.getX() + directionX, player.getY() + directionY, direct));
            enterF1 = false;
        }
    }

    public void updateBullet() {
        for (int i = 0; i < bulletList.size(); i++) {
            if (bulletList.get(i).direct.contains("TOP") && inBounds(bulletList.get(i).getX(), bulletList.get(i).getY() - 1)) {
                bulletList.get(i).setY(bulletList.get(i).getY() - 1);
            } else if (bulletList.get(i).direct.contains("BOT") && inBounds(bulletList.get(i).getX(), bulletList.get(i).getY() + 1)) {
                bulletList.get(i).setY(bulletList.get(i).getY() + 1);
            } else if (bulletList.get(i).direct.contains("LEFT") && inBounds(bulletList.get(i).getX() - 1, bulletList.get(i).getY())) {
                bulletList.get(i).setX(bulletList.get(i).getX() - 1);
            } else if (bulletList.get(i).direct.contains("RIGHT") && inBounds(bulletList.get(i).getX() + 1, bulletList.get(i).getY())) {
                bulletList.get(i).setX(bulletList.get(i).getX() + 1);
            } else {
//                System.out.printf("Bullet on place %s is out ", bulletList.get(i));
                bulletList.get(i).isalive = false;
            }
        }
    }

    public void updateEnemy() {
        int newPosX = 0;
        int newPosY = 0;
        int randomNum = 0;
        int currentEnemy = 0;
        String type = "WALL";
        for (int i = 0; i < allEnemies.size(); i++) {
            randomNum = rand.nextInt(4) + 1;
            if (randomNum == 1)
                newPosX = -1;
            else if (randomNum == 2)
                newPosX = 1;
            else if (randomNum == 3)
                newPosY = 1;
            else
                newPosY = -1;

            if (inBounds(allEnemies.get(i).getX() + newPosX, allEnemies.get(i).getY() + newPosY)) {
                allEnemies.get(i).tempPosX = newPosX;
                allEnemies.get(i).tempPosY = newPosY;
                boolean isIt2 = collided;
                if (!collisionDetection("WALL", 0)) {
                    if (allEnemies.get(i).type.contains("Stupid")) {
                        allEnemies.get(i).pattern(player, allEnemies.get(i), randomNum);
                        map[allEnemies.get(i).getY()][allEnemies.get(i).getX()] = 'E';
                    } else if (allEnemies.get(i).type.equals("Smart")) {
                        allEnemies.get(i).pattern(player, allEnemies.get(i), randomNum);
//                        map[allEnemies.get(i).getY()][allEnemies.get(i).getX()] = 'E'; // VILL VI INTE HA ETT E HÄR OCKSÅ?
                    }
                }
            }
            newPosX = 0;
            newPosY = 0;
        }
    }

    public void renderScores() {
        if (player.getHitPoints() <= 0) {
            player.setLost(true);
        }
        if (player.getHighScore() >= 500) {
            player.setWon(true);
        }

        int hp = player.hitPoints;
        int score = player.highScore;
        String s = Integer.toString(hp);
        String s2 = Integer.toString(score);
        char c;

        //// CHECKING FOR HITPOINTS
        if (s.length() == 3) {
            terminal.moveCursor(21, 2);
            c = s.charAt(0);
            terminal.putCharacter(c);
            terminal.moveCursor(22, 2);
            c = s.charAt(1);
            terminal.putCharacter(c);
            terminal.moveCursor(23, 2);
            c = s.charAt(2);
            terminal.putCharacter(c);
        } else if (s.length() == 2) {
            terminal.moveCursor(21, 2);
            c = s.charAt(0);
            terminal.putCharacter(c);
            terminal.moveCursor(22, 2);
            c = s.charAt(1);
            terminal.putCharacter(c);
        } else if (s.length() == 1) {
            terminal.moveCursor(21, 2);
            c = s.charAt(0);
            terminal.putCharacter(c);
        }
        //// CHECKING FOR HIGHSCORE
        if (s2.length() == 3) {
            terminal.moveCursor(32, 2);
            c = s2.charAt(0);
            terminal.putCharacter(c);
            terminal.moveCursor(33, 2);
            c = s2.charAt(1);
            terminal.putCharacter(c);
            terminal.moveCursor(34, 2);
            c = s2.charAt(2);
            terminal.putCharacter(c);
        } else if (s2.length() == 2) {
            terminal.moveCursor(32, 2);
            c = s2.charAt(0);
            terminal.putCharacter(c);
            terminal.moveCursor(33, 2);
            c = s2.charAt(1);
            terminal.putCharacter(c);
        } else if (s2.length() == 1) {
            terminal.moveCursor(32, 2);
            c = s2.charAt(0);
            terminal.putCharacter(c);
        }
    }

    public void updatePlayer(int newPosX, int newPosY) {
        player.tempPosX = newPosX;
        player.tempPosY = newPosY;

        if (inBounds(player.getX() + newPosX, player.getY() + newPosY)) {
            if (collisionDetection("POWERUP", 0)) {
                collectPowerUp();
            } else if (!collisionDetection("WALL", 0)) {
                map[player.getY()][player.getX()] = ' ';
                player.update(newPosX, newPosY);
            }

        }
    }

    public void renderStuff() {
        terminal.moveCursor(player.getX(), player.getY());
        map[player.getY()][player.getX()] = 'P';
        terminal.putCharacter('P');

        for (int i = 0; i < bulletList.size(); i++) {
            if (bulletList.get(i).isalive) {
                bulletList.get(i).setC('+');
                terminal.moveCursor(bulletList.get(i).getX(), bulletList.get(i).getY());
                terminal.putCharacter(bulletList.get(i).getC());
                map[bulletList.get(i).getY()][bulletList.get(i).getX()] = bulletList.get(i).getC();
            } else {
                bulletList.get(i).setC(' ');
                map[bulletList.get(i).getY()][bulletList.get(i).getX()] = bulletList.get(i).getC();
            }
        }

        for (int i = 0; i < allEnemies.size(); i++) {
            String type = "BULLET";

            //// IF MELEEATTACK OR RANGED ATTACK (BY BULLET)
            int specificEnemy = i;     /// USED IN collisionDetection for specific enemy colliding with bullet
            if (meleeAttack(i) && enterPressed && allEnemies.get(i).isAlive || collisionDetection(type, 0) && allEnemies.get(i).isAlive) {    //// AFTER MELEE ATTACK and ENTER IS PRESSED, ENEMY DIES
                player.setHighScore(player.getHighScore() + player.powerUpHighScore);
                renderScores();
                System.out.println(allEnemies.get(i));
                allEnemies.get(i).isAlive = false;
                enterPressed = false;
            }
            if (allEnemies.get(i).isAlive) {
                allEnemies.get(i).setC('E');
                terminal.moveCursor(allEnemies.get(i).getX(), allEnemies.get(i).getY());
                terminal.putCharacter('E');
                map[allEnemies.get(i).getY()][allEnemies.get(i).getX()] = 'E';
            } else {   // WHEN DEAD, REMOVE CHARACTER E
                allEnemies.get(i).setC(' ');
                map[allEnemies.get(i).getY()][allEnemies.get(i).getX()] = allEnemies.get(i).getC();
            }
        }
        for (int i = 0; i < allPowerups.size(); i++) {
            if (!allPowerups.get(i).isPickedUp()) {
                terminal.moveCursor(allPowerups.get(i).getX(), allPowerups.get(i).getY());
                if (allPowerups.get(i).getType().equals("HEALTH")) {
                    terminal.putCharacter('H');
                    map[allPowerups.get(i).getY()][allPowerups.get(i).getX()] = 'H';
                } else if (allPowerups.get(i).getType().equals("DAMAGE")) {
                    terminal.putCharacter('D');
                    map[allPowerups.get(i).getY()][allPowerups.get(i).getX()] = 'D';
                } else if (allPowerups.get(i).getType().equals("SCORE")) {
                    terminal.putCharacter('S');
                    map[allPowerups.get(i).getY()][allPowerups.get(i).getX()] = 'S';
                }
            } else {
                terminal.moveCursor(allPowerups.get(i).getX(), allPowerups.get(i).getY());
                terminal.putCharacter(' ');
                map[allPowerups.get(i).getY()][allPowerups.get(i).getX()] = ' ';
                allPowerups.remove(i);
            }
        }
        int i = 5;
    }

    public void collectPowerUp() { ////////////// ÄNDRAT 1/12
        for (int i = 0; i < allPowerups.size(); i++) {
            if (allPowerups.get(i).isPickedUp()) {
                allPowerups.get(i).powerUp(player);
            }
        }
    }

    public boolean inBounds(int x, int y) {
        boolean inBound = false;
        if (x < 68 && y < 23) {
            if (x > 0 && y > 3)
                inBound = true;
        }
        return inBound;
    }

    public void draw() {

    }

    public void clear() {
        terminal.clearScreen();
    }

    public boolean collisionDetection(String type, int currentEnemy) {
//        // CHECKS ONLY FOR COLLISION WITH POWERUP
        collided = false;
        if (type.contains("POWERUP")) {
            for (int i = 0; i < allPowerups.size(); i++) {      ////////////// ÄNDRAT 1/12
                if (map[player.getY() + player.getTempPosY()][player.getX() + player.getTempPosX()] == 'S' && allPowerups.get(i).getType().equals("SCORE")) { // ADD player.tempPos
                    allPowerups.get(i).setPickedUp(true);
                    collided = true;
                    break;
                } else if (map[player.getY() + player.getTempPosY()][player.getX() + player.getTempPosX()] == 'H' && allPowerups.get(i).getType().equals("HEALTH")) {
                    allPowerups.get(i).setPickedUp(true);
                    collided = true;
                    break;
                } else if (map[player.getY() + player.getTempPosY()][player.getX() + player.getTempPosX()] == 'D' && allPowerups.get(i).getType().equals("DAMAGE")) {
                    allPowerups.get(i).setPickedUp(true);
                    collided = true;
                    break;
                }

            }
        }
        if (type.contains("BULLET")) {
            for (int i = 0; i < bulletList.size(); i++) {      ////////////// ÄNDRAT 1/12
                if (map[bulletList.get(i).getY() + bulletList.get(i).getTempPosY()][bulletList.get(i).getX() + bulletList.get(i).getTempPosX()] == 'E' && bulletList.get(i).isalive) { // ADD player.tempPos
                    bulletList.get(i).isalive = false;
                    collided = true;
                    break;
                }
            }
        }
        if (type.contains("WALL")) {////////////// ÄNDRAT 1/12
            if (map[allEnemies.get(currentEnemy).getY() + allEnemies.get(currentEnemy).getTempPosY()][allEnemies.get(currentEnemy).getX() + allEnemies.get(currentEnemy).getTempPosX()] == 'W') {
                collided = true;
            } else if (map[player.getY() + player.getTempPosY()][player.getX() + player.getTempPosX()] == 'W') {
                collided = true;
            }
        }
        return collided;
    }

    public boolean meleeAttack(int placeInList) {   /// CHECKS IF PLAYER/ENEMY IS IN RANGE, and PLAYER gets true attack, enemy gets true inRange
        enemiesInrange = 0;
        attack = false;
        int pdistanceX = player.getX();
        int pdistanceY = player.getY();
        int edistanceX;
        int edistanceY;
        for (int i = 0; i < allEnemies.size(); i++) {
            edistanceX = allEnemies.get(placeInList).getX();
            edistanceY = allEnemies.get(placeInList).getY();
            if (pdistanceX - edistanceX == 1 || edistanceX - pdistanceX == 1 || pdistanceX - edistanceX == 0 || edistanceX - pdistanceX == 0) {
                if (pdistanceY - edistanceY == 1 || edistanceY - pdistanceY == 1 || pdistanceY - edistanceY == 0 || edistanceY - pdistanceY == 0) {
                    attack = true;
                    break;
                }
            }
        }
        for (int i = 0; i < allEnemies.size(); i++) {
            edistanceX = allEnemies.get(i).getX();
            edistanceY = allEnemies.get(i).getY();
            if (pdistanceX - edistanceX == 1 || edistanceX - pdistanceX == 1 || pdistanceX - edistanceX == 0 || edistanceX - pdistanceX == 0) {
                if (pdistanceY - edistanceY == 1 || edistanceY - pdistanceY == 1 || pdistanceY - edistanceY == 0 || edistanceY - pdistanceY == 0) {
                    allEnemies.get(i).inRange = true;
                    enemiesInrange++;
                }
            }
        }
        return attack;
    }
    ///////// FRÅN MENU ////////////////
}

