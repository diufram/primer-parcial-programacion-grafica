package com.graphics;

import com.graphics.formas.Sprite;

public class GeneradorFondo {
    private final Renderizador renderizador;
    private final Sprite cielo;
    private final Sprite suelo;
    private final Sprite nube;

    private static final int CIELO_COLUMNAS = 40;
    private static final int CIELO_FILAS = 20;
    private static final int SUELO_COLUMNAS = 40;
    private static final int SUELO_FILAS = 14;

    public GeneradorFondo(Renderizador renderizador) {
        this.renderizador = renderizador;
        this.cielo = crearSpriteCielo();
        this.suelo = crearSpriteSuelo();
        this.nube = crearSpriteNube();
    }

    public void dibujar() {
        dibujarCielo();
        dibujarNubes();
        dibujarSuelo();
    }

    private void dibujarCielo() {
        float pixelX = 2.0f / CIELO_COLUMNAS;
        float pixelY = 2.0f / CIELO_FILAS;
        cielo.dibujar(renderizador, -1.0f, 1.0f, pixelX, pixelY);
    }

    private void dibujarNubes() {
        dibujarNube(-0.75f, 0.72f, 0.75f);
        dibujarNube(0.10f, 0.80f, 0.62f);
        dibujarNube(0.78f, 0.62f, 0.70f);
        dibujarNube(-0.25f, 0.50f, 0.55f);
    }

    private void dibujarNube(float x, float y, float escala) {
        float p = 0.018f * escala;
        float left = x - p * 6.0f;
        float top = y + p * 2.0f;
        nube.dibujar(renderizador, left, top, p, p);
    }

    private void dibujarSuelo() {
        float pixelX = 2.0f / SUELO_COLUMNAS;
        float pixelY = 0.035f;
        float left = -1.0f;
        float top = Constantes.Y_CESPED + 0.11f;
        suelo.dibujar(renderizador, left, top, pixelX, pixelY);
    }

    private Sprite crearSpriteCielo() {
        Sprite s = new Sprite();
        float[] cieloArriba = { 0.42f, 0.80f, 0.95f };
        float[] cieloAbajo = { 0.55f, 0.86f, 0.96f };
        for (int y = 0; y < CIELO_FILAS; y++) {
            float t = y / (float) (CIELO_FILAS - 1);
            float[] c = {
                interpolar(cieloArriba[0], cieloAbajo[0], t),
                interpolar(cieloArriba[1], cieloAbajo[1], t),
                interpolar(cieloArriba[2], cieloAbajo[2], t)
            };
            s.agregarRect(0, y, CIELO_COLUMNAS, 1, c);
        }
        return s;
    }

    private Sprite crearSpriteNube() {
        Sprite s = new Sprite();
        float[] blanco = { 0.96f, 1.00f, 1.00f };
        float[] sombra = { 0.82f, 0.94f, 0.96f };

        s.agregarRect(1, 5, 8, 1, sombra);
        s.agregarRect(8, 5, 4, 1, sombra);
        s.agregarRect(0, 4, 3, 1, blanco);
        s.agregarRect(2, 3, 4, 2, blanco);
        s.agregarRect(5, 2, 4, 3, blanco);
        s.agregarRect(8, 3, 3, 2, blanco);
        s.agregarRect(10, 4, 2, 1, blanco);
        s.agregarRect(1, 5, 11, 1, blanco);
        return s;
    }

    private Sprite crearSpriteSuelo() {
        Sprite s = new Sprite();

        float[] verdeOscuro = { 0.20f, 0.55f, 0.18f };
        float[] verdeBase = { 0.36f, 0.78f, 0.22f };
        float[] verdeClaro = { 0.65f, 0.96f, 0.34f };

        float[] tierraBase = { 0.72f, 0.52f, 0.25f };
        float[] tierraClara = { 0.86f, 0.66f, 0.34f };
        float[] tierraOscura = { 0.55f, 0.36f, 0.18f };

        s.agregarRect(0, 0, SUELO_COLUMNAS, 8, tierraBase);
        s.agregarRect(0, 8, SUELO_COLUMNAS, 1, verdeOscuro);
        s.agregarRect(0, 9, SUELO_COLUMNAS, 2, verdeBase);
        s.agregarRect(0, 11, SUELO_COLUMNAS, 1, verdeClaro);

        for (int fila = 0; fila < 4; fila++) {
            for (int col = 0; col < SUELO_COLUMNAS; col++) {
                float[] c = ((fila + col) % 2 == 0) ? tierraClara : tierraOscura;
                s.agregarPixel(col, fila + 1, c);
            }
        }

        for (int i = 0; i < SUELO_COLUMNAS; i += 2) {
            s.agregarPixel(i, 12, verdeClaro);
            if (i + 1 < SUELO_COLUMNAS && (i / 2) % 2 == 0) {
                s.agregarPixel(i + 1, 13, verdeClaro);
            }
        }

        return s;
    }

    private float interpolar(float a, float b, float t) {
        return a + (b - a) * t;
    }
}
