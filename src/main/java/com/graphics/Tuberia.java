package com.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Tuberia {
    private float x;
    private float gapCentroY;
    private boolean puntuada;

    private final float ancho;
    private final float gapAlto;

    private final int ubicacionOffset;
    private final int ubicacionEscala;
    private final int ubicacionColor;

    public Tuberia(float x, float gapCentroY, float ancho, float gapAlto,
            int ubicacionOffset, int ubicacionEscala, int ubicacionColor) {
        this.x = x;
        this.gapCentroY = gapCentroY;
        this.ancho = ancho;
        this.gapAlto = gapAlto;
        this.ubicacionOffset = ubicacionOffset;
        this.ubicacionEscala = ubicacionEscala;
        this.ubicacionColor = ubicacionColor;
        this.puntuada = false;
    }

    public void actualizar(float velX, float dt) {
        x -= velX * dt;
    }

    public float getX() {
        return x;
    }

    public float getAncho() {
        return ancho;
    }

    public float getGapCentroY() {
        return gapCentroY;
    }

    public float getGapAlto() {
        return gapAlto;
    }

    public boolean estaPuntuada() {
        return puntuada;
    }

    public void setPuntuada(boolean puntuada) {
        this.puntuada = puntuada;
    }

    public float getGapSuperior() {
        return gapCentroY + (gapAlto * 0.5f);
    }

    public float getGapInferior() {
        return gapCentroY - (gapAlto * 0.5f);
    }

    public float getAltoSuperior() {
        return 1.0f - getGapSuperior();
    }

    public float getAltoInferior() {
        return getGapInferior() + 1.0f;
    }

    public float getYCentroSuperior() {
        return getGapSuperior() + (getAltoSuperior() * 0.5f);
    }

    public float getYCentroInferior() {
        return -1.0f + (getAltoInferior() * 0.5f);
    }

    public boolean estaFueraDePantalla() {
        return x + (ancho * 0.5f) < -1.3f;
    }

    public boolean pasoPorX(float birdX) {
        return x + (ancho * 0.5f) < birdX && !puntuada;
    }

    public void dibujar(float r, float g, float b) {
        float altoSuperior = getAltoSuperior();

        if (altoSuperior > 0.0f) {
            dibujarTuberiaPixel(
                    x,
                    getYCentroSuperior(),
                    ancho,
                    altoSuperior,
                    true);
        }

        float altoInferior = getAltoInferior();

        if (altoInferior > 0.0f) {
            dibujarTuberiaPixel(
                    x,
                    getYCentroInferior(),
                    ancho,
                    altoInferior,
                    false);
        }
    }

    private void dibujarTuberiaPixel(float x, float yCentro, float ancho, float alto, boolean esSuperior) {
        float borde = ancho * 0.055f;

        float[] bordeOscuro = { 0.12f, 0.08f, 0.12f };
        float[] verdeOscuro = { 0.15f, 0.34f, 0.13f };
        float[] verdeBase = { 0.30f, 0.68f, 0.25f };
        float[] verdeClaro = { 0.55f, 0.93f, 0.40f };
        float[] verdeMedio = { 0.24f, 0.56f, 0.20f };
        float[] sombra = { 0.10f, 0.27f, 0.10f };
        float[] brillo = { 0.72f, 1.00f, 0.55f };

        // Borde exterior del cuerpo
        dibujarRect(
                x,
                yCentro,
                ancho,
                alto,
                bordeOscuro[0],
                bordeOscuro[1],
                bordeOscuro[2]);

        // Interior verde
        dibujarRect(
                x,
                yCentro,
                ancho - borde * 2.0f,
                alto - borde * 2.0f,
                verdeBase[0],
                verdeBase[1],
                verdeBase[2]);

        float altoInterior = alto - borde * 2.0f;

        // Franja clara izquierda
        dibujarRect(
                x - ancho * 0.23f,
                yCentro,
                ancho * 0.16f,
                altoInterior,
                verdeClaro[0],
                verdeClaro[1],
                verdeClaro[2]);

        // Franja verde media
        dibujarRect(
                x - ancho * 0.03f,
                yCentro,
                ancho * 0.22f,
                altoInterior,
                verdeMedio[0],
                verdeMedio[1],
                verdeMedio[2]);

        // Sombra derecha
        dibujarRect(
                x + ancho * 0.25f,
                yCentro,
                ancho * 0.20f,
                altoInterior,
                sombra[0],
                sombra[1],
                sombra[2]);

        // Línea oscura vertical
        dibujarRect(
                x + ancho * 0.18f,
                yCentro,
                ancho * 0.035f,
                altoInterior,
                verdeOscuro[0],
                verdeOscuro[1],
                verdeOscuro[2]);

        // Brillo fino derecho
        dibujarRect(
                x + ancho * 0.43f,
                yCentro,
                ancho * 0.045f,
                altoInterior,
                brillo[0],
                brillo[1],
                brillo[2]);

        // Cabeza de la tubería
        float cabezaAlto = Math.min(0.16f, alto * 0.35f);
        float cabezaAncho = ancho * 1.22f;

        float cabezaY;

        if (esSuperior) {
            // La tubería superior tiene la cabeza abajo
            cabezaY = yCentro - (alto * 0.5f) + (cabezaAlto * 0.5f);
        } else {
            // La tubería inferior tiene la cabeza arriba
            cabezaY = yCentro + (alto * 0.5f) - (cabezaAlto * 0.5f);
        }

        dibujarCabezaTuberia(
                x,
                cabezaY,
                cabezaAncho,
                cabezaAlto,
                esSuperior);
    }

    private void dibujarCabezaTuberia(float x, float y, float ancho, float alto, boolean esSuperior) {
        float borde = ancho * 0.045f;

        float[] bordeOscuro = { 0.12f, 0.08f, 0.12f };
        float[] verdeBase = { 0.34f, 0.76f, 0.28f };
        float[] verdeClaro = { 0.65f, 1.00f, 0.45f };
        float[] verdeMedio = { 0.28f, 0.65f, 0.24f };
        float[] sombra = { 0.13f, 0.33f, 0.12f };
        float[] brillo = { 0.75f, 1.00f, 0.58f };

        // Borde exterior de la cabeza
        dibujarRect(
                x,
                y,
                ancho,
                alto,
                bordeOscuro[0],
                bordeOscuro[1],
                bordeOscuro[2]);

        // Interior de la cabeza
        dibujarRect(
                x,
                y,
                ancho - borde * 2.0f,
                alto - borde * 2.0f,
                verdeBase[0],
                verdeBase[1],
                verdeBase[2]);

        float altoInterior = alto - borde * 2.0f;

        // Franja clara izquierda
        dibujarRect(
                x - ancho * 0.25f,
                y,
                ancho * 0.18f,
                altoInterior,
                verdeClaro[0],
                verdeClaro[1],
                verdeClaro[2]);

        // Verde medio central
        dibujarRect(
                x - ancho * 0.02f,
                y,
                ancho * 0.25f,
                altoInterior,
                verdeMedio[0],
                verdeMedio[1],
                verdeMedio[2]);

        // Sombra derecha
        dibujarRect(
                x + ancho * 0.27f,
                y,
                ancho * 0.18f,
                altoInterior,
                sombra[0],
                sombra[1],
                sombra[2]);

        // Brillo fino derecho
        dibujarRect(
                x + ancho * 0.44f,
                y,
                ancho * 0.04f,
                altoInterior,
                brillo[0],
                brillo[1],
                brillo[2]);

        // Línea horizontal de brillo
        float lineaY;

        if (esSuperior) {
            lineaY = y - alto * 0.34f;
        } else {
            lineaY = y + alto * 0.34f;
        }

        dibujarRect(
                x - ancho * 0.18f,
                lineaY,
                ancho * 0.55f,
                alto * 0.08f,
                brillo[0],
                brillo[1],
                brillo[2]);

        // Línea oscura inferior/superior para efecto pixelado
        float lineaOscuraY;

        if (esSuperior) {
            lineaOscuraY = y + alto * 0.42f;
        } else {
            lineaOscuraY = y - alto * 0.42f;
        }

        dibujarRect(
                x,
                lineaOscuraY,
                ancho * 0.90f,
                alto * 0.06f,
                bordeOscuro[0],
                bordeOscuro[1],
                bordeOscuro[2]);
    }

    private void dibujarRect(float x, float y, float ancho, float alto, float r, float g, float b) {
        GL20.glUniform2f(ubicacionOffset, x, y);
        GL20.glUniform2f(ubicacionEscala, ancho, alto);
        GL20.glUniform3f(ubicacionColor, r, g, b);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
    }
}