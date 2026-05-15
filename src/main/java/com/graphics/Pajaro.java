package com.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Pajaro {
    private float x;
    private float y;
    private float velocidadY;

    private final float ancho;
    private final float alto;
    private final float gravedad;
    private final float impulsoSalto;
    private final float velocidadMaxCaida;
    private final float[] color;

    private final int uOffsetLocation;
    private final int uScaleLocation;
    private final int uColorLocation;

    private boolean vivo;

    private boolean animandoAla;
    private float timerAla;

    private static final float DURACION_ALETEO_SALTO = 0.24f;

    private float tiempoAnimacion;
    private final boolean varianteAzul;

    private static final String[] CUERPO = {
        "000000000KKKK00000000",
        "0000000KKKHHHKK000000",
        "000000KHHYYYKWWK00000",
        "000KKKKYYYYKWWWWK0000",
        "00K0000KYYSKWWWWWK000",
        "00K0000KKYYK00WWWK000",
        "0KK0000KHHYSK0WWKK000",
        "KKYKKKKYYYSKKKKKRKRK0",
        "KSSYYYYYYYKDRRRRRRRK0",
        "0KSSYYYYYYKDRRKKKKKK0",
        "00KSSSSSSSKDRRRRRKK00",
        "000KSSSSSSSKDDDDDDK00",
        "0000KKSSSSSSKKKKKK000",
        "000000KKKKKK000000000"
    };

    private static final String[] ALA_ARRIBA = {
        "000000000000000000000",
        "000000000000000000000",
        "0000KKKK0000000000000",
        "000KHHHHK000000000000",
        "00KHYYYYK000000000000",
        "00KYYYYYK000000000000",
        "000KSSSSK000000000000",
        "0000KKKK0000000000000",
        "000000000000000000000",
        "000000000000000000000",
        "000000000000000000000",
        "000000000000000000000",
        "000000000000000000000",
        "000000000000000000000"
    };

    private static final String[] ALA_MEDIA = {
        "000000000000000000000",
        "000000000000000000000",
        "000000000000000000000",
        "0000KKKK0000000000000",
        "000KHHHHK000000000000",
        "00KHYYYYK000000000000",
        "00KYYYYYK000000000000",
        "000KSSSSK000000000000",
        "0000KKKK0000000000000",
        "000000000000000000000",
        "000000000000000000000",
        "000000000000000000000",
        "000000000000000000000",
        "000000000000000000000"
    };

    private static final String[] ALA_ABAJO = {
        "000000000000000000000",
        "000000000000000000000",
        "000000000000000000000",
        "000000000000000000000",
        "0000KKKK0000000000000",
        "000KHHHHK000000000000",
        "00KHYYYYK000000000000",
        "00KYYYYYK000000000000",
        "000KSSSSK000000000000",
        "0000KKKK0000000000000",
        "000000000000000000000",
        "000000000000000000000",
        "000000000000000000000",
        "000000000000000000000"
    };

    public Pajaro(float x, float y, float gravedad, float impulsoSalto, float velocidadMaxCaida,
            float ancho, float alto, float[] color,
            int uOffsetLocation, int uScaleLocation, int uColorLocation) {
        this.x = x;
        this.y = y;
        this.velocidadY = 0f;

        this.gravedad = gravedad;
        this.impulsoSalto = impulsoSalto;
        this.velocidadMaxCaida = velocidadMaxCaida;

        this.ancho = ancho;
        this.alto = alto;
        this.color = color;

        this.uOffsetLocation = uOffsetLocation;
        this.uScaleLocation = uScaleLocation;
        this.uColorLocation = uColorLocation;

        this.vivo = true;

        this.animandoAla = false;
        this.timerAla = 0f;

        this.tiempoAnimacion = 0f;
        this.varianteAzul = color[2] > color[0];
    }

    public void saltar() {
        if (vivo) {
            velocidadY = impulsoSalto;
            animandoAla = true;
            timerAla = 0f;
        }
    }

    public void actualizar(float dt) {
        if (!vivo) {
            return;
        }

        tiempoAnimacion += dt;

        velocidadY += gravedad * dt;

        if (velocidadY < velocidadMaxCaida) {
            velocidadY = velocidadMaxCaida;
        }

        y += velocidadY * dt;

        if (animandoAla) {
            timerAla += dt;

            if (timerAla >= DURACION_ALETEO_SALTO) {
                timerAla = 0f;
                animandoAla = false;
            }
        }
    }

    public boolean estaVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getAncho() {
        return ancho;
    }

    public float getAlto() {
        return alto;
    }

    public boolean colisionaSuelo() {
        return y - (alto * 0.5f) <= -1.0f;
    }

    public boolean colisionaTecho() {
        return y + (alto * 0.5f) >= 1.0f;
    }

    public void dibujar() {
        float bob = (float) Math.sin(tiempoAnimacion * 10.0f) * 0.003f;
        float cx = x;
        float cy = y + bob;

        int wingFrame = obtenerFrameAla();
        dibujarPixelArt(cx, cy, wingFrame);
    }

    private int obtenerFrameAla() {
        if (animandoAla) {
            float progreso = timerAla / DURACION_ALETEO_SALTO;
            if (progreso < 0.33f) return 0;
            if (progreso < 0.66f) return 1;
            return 2;
        }

        float t = (float) Math.sin(tiempoAnimacion * 18.0f);
        if (t > 0.35f) return 0;
        if (t < -0.35f) return 2;
        return 1;
    }

    private void dibujarPixelArt(float cx, float cy, int wingFrame) {
        String[] ala = obtenerSpriteAla(wingFrame);

        int filas = CUERPO.length;
        int columnas = CUERPO[0].length();
        float pixel = Math.min(ancho / columnas, alto / filas) * 1.15f;

        float left = cx - (columnas * pixel) * 0.5f;
        float top = cy + (filas * pixel) * 0.5f;

        for (int fila = 0; fila < filas; fila++) {
            for (int col = 0; col < columnas; col++) {
                char ch = CUERPO[fila].charAt(col);
                if (ch == '0') continue;
                float[] c = colorPixel(ch);
                dibujarPixel(left, top, pixel, col, fila, c);
            }
        }

        for (int fila = 0; fila < ala.length; fila++) {
            for (int col = 0; col < ala[fila].length(); col++) {
                char ch = ala[fila].charAt(col);
                if (ch == '0') continue;
                float[] c = colorPixel(ch);
                dibujarPixel(left, top, pixel, col, fila, c);
            }
        }
    }

    private String[] obtenerSpriteAla(int wingFrame) {
        return switch (wingFrame) {
            case 0 -> ALA_ARRIBA;
            case 2 -> ALA_ABAJO;
            default -> ALA_MEDIA;
        };
    }

    private float[] colorPixel(char ch) {
        if (varianteAzul) {
            return switch (ch) {
                case 'K' -> new float[] { 0.15f, 0.20f, 0.35f };
                case 'Y' -> new float[] { color[0], color[1], color[2] };
                case 'S' -> new float[] { color[0] * 0.80f, color[1] * 0.85f, color[2] * 0.90f };
                case 'H' -> new float[] { Math.min(1f, color[0] * 1.20f), Math.min(1f, color[1] * 1.20f), Math.min(1f, color[2] * 1.20f) };
                case 'R' -> new float[] { 0.20f, 0.25f, 0.60f };
                case 'D' -> new float[] { 0.12f, 0.15f, 0.45f };
                case 'W' -> new float[] { 0.50f, 0.65f, 1.00f };
                default -> new float[] { 1f, 1f, 1f };
            };
        }
        return switch (ch) {
            case 'K' -> new float[] { 0.22f, 0.13f, 0.17f };
            case 'Y' -> new float[] { 0.99f, 0.85f, 0.26f };
            case 'S' -> new float[] { 0.94f, 0.75f, 0.24f };
            case 'H' -> new float[] { 0.99f, 0.92f, 0.52f };
            case 'R' -> new float[] { 0.87f, 0.00f, 0.02f };
            case 'D' -> new float[] { 0.68f, 0.06f, 0.07f };
            case 'W' -> new float[] { 0.91f, 1.00f, 0.85f };
            default -> new float[] { 1f, 1f, 1f };
        };
    }

    private void dibujarPixel(float left, float top, float pixel, int gx, int gy, float[] c) {
        float px = left + gx * pixel + pixel * 0.5f;
        float py = top - gy * pixel - pixel * 0.5f;
        dibujarRect(px, py, pixel, pixel, c[0], c[1], c[2]);
    }

    private void dibujarRect(float x, float y, float ancho, float alto, float r, float g, float b) {
        GL20.glUniform2f(uOffsetLocation, x, y);
        GL20.glUniform2f(uScaleLocation, ancho, alto);
        GL20.glUniform3f(uColorLocation, r, g, b);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
    }

    public void reiniciar(float nuevoY) {
        y = nuevoY;
        velocidadY = 0f;
        vivo = true;
        animandoAla = false;
        timerAla = 0f;
        tiempoAnimacion = 0f;
    }
}