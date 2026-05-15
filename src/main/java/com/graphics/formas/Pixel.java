package com.graphics.formas;

public class Pixel {
    private final int x;
    private final int y;
    private final float[] color;

    public Pixel(int x, int y, float[] color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float[] getColor() {
        return color;
    }
}
