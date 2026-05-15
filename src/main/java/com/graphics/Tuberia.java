package com.graphics;

import com.graphics.formas.Sprite;

public class Tuberia {
    private float x;
    private float gapCentroY;
    private boolean puntuada;

    private final float ancho;
    private final float gapAlto;
    private final Renderizador renderizador;

    private static final int CUERPO_COLUMNAS = 12;
    private static final int CABEZA_COLUMNAS = 14;
    private static final int CABEZA_FILAS = 4;

    public Tuberia(float x, float gapCentroY, float ancho, float gapAlto, Renderizador renderizador) {
        this.x = x;
        this.gapCentroY = gapCentroY;
        this.ancho = ancho;
        this.gapAlto = gapAlto;
        this.renderizador = renderizador;
        this.puntuada = false;
    }

    public void actualizar(float velX, float dt) { x -= velX * dt; }
    public float getX() { return x; }
    public float getAncho() { return ancho; }
    public float getGapCentroY() { return gapCentroY; }
    public float getGapAlto() { return gapAlto; }
    public boolean estaPuntuada() { return puntuada; }
    public void setPuntuada(boolean puntuada) { this.puntuada = puntuada; }
    public float getGapSuperior() { return gapCentroY + (gapAlto * 0.5f); }
    public float getGapInferior() { return gapCentroY - (gapAlto * 0.5f); }
    public float getAltoSuperior() { return 1.0f - getGapSuperior(); }
    public float getAltoInferior() { return getGapInferior() + 1.0f; }
    public float getYCentroSuperior() { return getGapSuperior() + (getAltoSuperior() * 0.5f); }
    public float getYCentroInferior() { return -1.0f + (getAltoInferior() * 0.5f); }
    public boolean estaFueraDePantalla() { return x + (ancho * 0.5f) < -1.3f; }
    public boolean pasoPorX(float birdX) { return x + (ancho * 0.5f) < birdX && !puntuada; }

    public void dibujar(float r, float g, float b) {
        float altoSuperior = getAltoSuperior();
        if (altoSuperior > 0.0f) {
            dibujarBloqueTuberia(x, getYCentroSuperior(), ancho, altoSuperior, true);
        }

        float altoInferior = getAltoInferior();
        if (altoInferior > 0.0f) {
            dibujarBloqueTuberia(x, getYCentroInferior(), ancho, altoInferior, false);
        }
    }

    private void dibujarBloqueTuberia(float cx, float cy, float bloqueAncho, float bloqueAlto, boolean superior) {
        float pixelX = bloqueAncho / CUERPO_COLUMNAS;
        int filas = Math.max(6, Math.round(bloqueAlto / pixelX));
        float pixelY = bloqueAlto / filas;

        Sprite cuerpo = crearSpriteCuerpo(filas);

        float left = cx - bloqueAncho * 0.5f;
        float top = cy + bloqueAlto * 0.5f;
        cuerpo.dibujar(renderizador, left, top, pixelX, pixelY);

        float cabezaAlto = Math.min(0.16f, bloqueAlto * 0.35f);
        float cabezaAncho = bloqueAncho * 1.22f;
        float cabezaY = superior
            ? cy - (bloqueAlto * 0.5f) + (cabezaAlto * 0.5f)
            : cy + (bloqueAlto * 0.5f) - (cabezaAlto * 0.5f);
        dibujarCabeza(cx, cabezaY, cabezaAncho, cabezaAlto, superior);
    }

    private void dibujarCabeza(float cx, float cy, float anchoCabeza, float altoCabeza, boolean superior) {
        Sprite cabeza = crearSpriteCabeza(superior);

        float pixelX = anchoCabeza / CABEZA_COLUMNAS;
        float pixelY = altoCabeza / CABEZA_FILAS;
        float left = cx - anchoCabeza * 0.5f;
        float top = cy + altoCabeza * 0.5f;
        cabeza.dibujar(renderizador, left, top, pixelX, pixelY);
    }

    private Sprite crearSpriteCuerpo(int filas) {
        float[] bordeOscuro = { 0.12f, 0.08f, 0.12f };
        float[] verdeOscuro = { 0.15f, 0.34f, 0.13f };
        float[] verdeBase = { 0.34f, 0.76f, 0.28f };
        float[] verdeBaseCuerpo = { 0.30f, 0.68f, 0.25f };
        float[] verdeClaro = { 0.65f, 1.00f, 0.45f };
        float[] verdeMedio = { 0.28f, 0.65f, 0.24f };
        float[] sombra = { 0.13f, 0.33f, 0.12f };
        float[] brillo = { 0.75f, 1.00f, 0.58f };

        Sprite cuerpo = new Sprite();
        for (int y = 0; y < filas; y++) {
            for (int x = 0; x < CUERPO_COLUMNAS; x++) {
                cuerpo.agregarPixel(x, y, verdeBaseCuerpo);
            }
        }

        cuerpo.agregarRect(0, 0, 1, filas, bordeOscuro);
        cuerpo.agregarRect(CUERPO_COLUMNAS - 1, 0, 1, filas, bordeOscuro);
        cuerpo.agregarRect(1, 0, 2, filas, verdeClaro);
        cuerpo.agregarRect(3, 0, 3, filas, verdeMedio);
        cuerpo.agregarRect(8, 0, 1, filas, verdeOscuro);
        cuerpo.agregarRect(9, 0, 1, filas, sombra);
        cuerpo.agregarRect(10, 0, 2, filas, brillo);

        return cuerpo;
    }

    private Sprite crearSpriteCabeza(boolean superior) {
        float[] bordeOscuro = { 0.12f, 0.08f, 0.12f };
        float[] verdeBase = { 0.34f, 0.76f, 0.28f };
        float[] verdeClaro = { 0.65f, 1.00f, 0.45f };
        float[] verdeMedio = { 0.28f, 0.65f, 0.24f };
        float[] sombra = { 0.13f, 0.33f, 0.12f };
        float[] brillo = { 0.75f, 1.00f, 0.58f };

        Sprite cabeza = new Sprite();
        for (int y = 0; y < CABEZA_FILAS; y++) {
            for (int x = 0; x < CABEZA_COLUMNAS; x++) {
                cabeza.agregarPixel(x, y, verdeBase);
            }
        }

        cabeza.agregarRect(0, 0, 1, CABEZA_FILAS, bordeOscuro);
        cabeza.agregarRect(CABEZA_COLUMNAS - 1, 0, 1, CABEZA_FILAS, bordeOscuro);
        cabeza.agregarRect(1, 0, 2, CABEZA_FILAS, verdeClaro);
        cabeza.agregarRect(3, 0, 4, CABEZA_FILAS, verdeMedio);
        cabeza.agregarRect(9, 0, 2, CABEZA_FILAS, sombra);
        cabeza.agregarRect(11, 0, 2, CABEZA_FILAS, brillo);

        int filaBrillo = superior ? 2 : 1;
        int filaSombra = superior ? 1 : 2;
        cabeza.agregarRect(3, filaBrillo, 8, 1, brillo);
        cabeza.agregarRect(1, filaSombra, 12, 1, bordeOscuro);

        return cabeza;
    }
}
