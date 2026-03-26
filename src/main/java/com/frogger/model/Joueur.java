package com.frogger.model;

public class Joueur {

    private int x;
    private int y;

    public Joueur(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean deplacer(int dx, int dy, int maxX, int maxY) {
        int nx = x + dx;
        int ny = y + dy;

        if (nx < 0 || nx >= maxX || ny < 0 || ny >= maxY) return false;

        x = nx;
        y = ny;
        return true;
    }

    public void reinitialiser(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }
}

