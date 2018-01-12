package com.company;

public interface  Powerup {

    public void setPickedUp(boolean pickedUp);
    public boolean isPickedUp();
    public int getX();

    public void setX(int x);

    public int getY();

    public void setY(int y);

    public String getType();

    public void setType(String type);

    public void powerUp(Player player);
}
