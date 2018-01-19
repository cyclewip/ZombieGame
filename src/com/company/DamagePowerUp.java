package com.company;

public class DamagePowerUp implements Powerup {
    public int x = 0;
    public int y = 0;
    public String type = "DAMAGE";
    boolean pickedUp = false;
    char c = '★';

    public char getC() {
        return c;
    }

    public void setC(char c) {
        this.c = c;
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    public DamagePowerUp(int x, int y) {
        this.x = x;

        this.y = y;
    }

    public void powerUp(Player player) {
        player.setPowerUpDamage(player.getPowerUpDamage() + 5);
    }

}
