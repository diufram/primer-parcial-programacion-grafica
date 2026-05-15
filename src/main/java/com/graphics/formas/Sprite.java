package com.graphics.formas;

import com.graphics.Renderizador;
import java.util.ArrayList;
import java.util.List;

public class Sprite {
    private final List<Pixel> pixeles;

    public Sprite() {
        this.pixeles = new ArrayList<>();
    }

    public void agregarPixel(int x, int y, float[] color) {
        pixeles.add(new Pixel(x, y, color));
    }

    public void agregarRect(int x, int y, int ancho, int alto, float[] color) {
        for (int py = y; py < y + alto; py++) {
            for (int px = x; px < x + ancho; px++) {
                agregarPixel(px, py, color);
            }
        }
    }

    public void dibujar(Renderizador r, float x, float y, float escala) {
        dibujar(r, x, y, escala, escala);
    }

    public void dibujar(Renderizador r, float x, float y, float escalaX, float escalaY) {
        for (Pixel pixel : pixeles) {
            float px = x + pixel.getX() * escalaX + escalaX * 0.5f;
            float py = y - pixel.getY() * escalaY - escalaY * 0.5f;
            float[] c = pixel.getColor();
            r.dibujarRect(px, py, escalaX, escalaY, c[0], c[1], c[2]);
        }
    }

    public List<Pixel> getPixeles() {
        return pixeles;
    }
}
