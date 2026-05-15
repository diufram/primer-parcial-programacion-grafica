package com.graphics;

import com.graphics.formas.Sprite;
import com.graphics.formas.SpriteAnimado;

public class PajaroVisual {
    private static final float DURACION_ALETEO_SALTO = 0.24f;
    private static final int GRID_W = 16;
    private static final int GRID_H = 12;

    private static final int ALA_ARRIBA_IDX = 0;
    private static final int ALA_MEDIA_IDX = 1;
    private static final int ALA_ABAJO_IDX = 2;

    private final Renderizador renderizador;
    private final float ancho;
    private final float alto;
    private final Sprite cuerpoSprite;
    private final SpriteAnimado alas;

    private boolean animandoAla;
    private float timerAla;
    private float tiempoAnimacion;

    public PajaroVisual(Renderizador renderizador, float ancho, float alto, float[] colorBase) {
        this.renderizador = renderizador;
        this.ancho = ancho;
        this.alto = alto;
        this.animandoAla = false;
        this.timerAla = 0f;
        this.tiempoAnimacion = 0f;

        boolean varianteAzul = colorBase[2] > colorBase[0];
        this.cuerpoSprite = crearCuerpo(varianteAzul);
        this.alas = new SpriteAnimado(
            crearAla(varianteAzul, -1),
            crearAla(varianteAzul, 0),
            crearAla(varianteAzul, 1)
        );
    }

    public void onSaltar() {
        animandoAla = true;
        timerAla = 0f;
    }

    public void actualizar(float dt) {
        tiempoAnimacion += dt;
        if (animandoAla) {
            timerAla += dt;
            if (timerAla >= DURACION_ALETEO_SALTO) {
                timerAla = 0f;
                animandoAla = false;
            }
        }
    }

    public void dibujar(float x, float y) {
        float bob = (float) Math.sin(tiempoAnimacion * 10.0f) * 0.003f;
        float cx = x;
        float cy = y + bob;
        float pixel = Math.min(ancho / GRID_W, alto / GRID_H) * 1.10f;
        float left = cx - (GRID_W * pixel) * 0.5f;
        float top = cy + (GRID_H * pixel) * 0.5f;

        cuerpoSprite.dibujar(renderizador, left, top, pixel);
        alas.getFrame(obtenerFrameAla()).dibujar(renderizador, left, top, pixel);
    }

    public void reiniciar() {
        animandoAla = false;
        timerAla = 0f;
        tiempoAnimacion = 0f;
    }

    private int obtenerFrameAla() {
        if (animandoAla) {
            float progreso = timerAla / DURACION_ALETEO_SALTO;
            if (progreso < 0.33f) return ALA_ARRIBA_IDX;
            if (progreso < 0.66f) return ALA_MEDIA_IDX;
            return ALA_ABAJO_IDX;
        }
        float t = (float) Math.sin(tiempoAnimacion * 18.0f);
        if (t > 0.35f) return ALA_ARRIBA_IDX;
        if (t < -0.35f) return ALA_ABAJO_IDX;
        return ALA_MEDIA_IDX;
    }

    private Sprite crearCuerpo(boolean varianteAzul) {
        float[] negro = varianteAzul ? new float[] {0.15f, 0.20f, 0.35f} : new float[] {0.22f, 0.13f, 0.17f};
        float[] blanco = varianteAzul ? new float[] {0.88f, 0.93f, 1.00f} : new float[] {0.95f, 0.95f, 0.95f};
        float[] amarillo = varianteAzul ? new float[] {0.50f, 0.70f, 1.00f} : Constantes.COLOR_JUGADOR1;
        float[] amarilloSombra = varianteAzul ? new float[] {0.38f, 0.56f, 0.90f} : new float[] {0.94f, 0.75f, 0.24f};
        float[] rojo = varianteAzul ? new float[] {0.30f, 0.35f, 0.70f} : new float[] {0.87f, 0.00f, 0.02f};
        float[] rojoOscuro = varianteAzul ? new float[] {0.18f, 0.22f, 0.52f} : new float[] {0.68f, 0.06f, 0.07f};

        Sprite s = new Sprite();
        agregar(s, new int[][] {
            {4,1},{5,1},{6,1},{7,1},{8,1},{9,1},{10,1},{3,2},{4,2},{10,2},{11,2},
            {2,3},{3,3},{11,3},{12,3},{1,4},{2,4},{12,4},{13,4},{1,5},{13,5},
            {1,6},{13,6},{2,7},{3,7},{12,7},{13,7},{3,8},{4,8},{11,8},{12,8},
            {4,9},{5,9},{9,9},{10,9},{5,10},{6,10},{7,10},{8,10}
        }, negro);

        agregar(s, new int[][] {
            {5,2},{6,2},{7,2},{8,2},{9,2},{4,3},{8,3},{9,3},{10,3},{3,4},{4,4},
            {9,4},{10,4},{11,4},{2,5},{3,5},{4,5},{10,5},{11,5},{12,5},{2,6},
            {3,6},{10,6},{11,6},{12,6},{4,7},{10,7},{11,7},{5,8},{6,8},{10,8}
        }, blanco);

        agregar(s, new int[][] {
            {5,3},{6,3},{7,3},{5,4},{6,4},{7,4},{8,4},{5,5},{6,5},{7,5},{8,5},{9,5},
            {4,6},{5,6},{6,6},{7,6},{8,6},{9,6},{4,7},{5,7},{6,7},{7,7},{8,7},{9,7},
            {6,9},{7,9},{8,9}
        }, amarillo);

        agregar(s, new int[][] {{4,8},{5,9},{6,10}}, amarilloSombra);
        agregar(s, new int[][] {
            {10,5},{11,5},{12,5},{13,5},{14,5},{10,6},{11,6},{12,6},{13,6},{14,6},
            {10,7},{11,7},{12,7},{13,7}
        }, rojo);
        agregar(s, new int[][] {{10,8},{11,8},{12,8},{13,8}}, rojoOscuro);
        return s;
    }

    private Sprite crearAla(boolean varianteAzul, int desplazamientoY) {
        float[] alaClaro = varianteAzul ? new float[] {0.70f, 0.84f, 1.00f} : new float[] {0.99f, 0.92f, 0.52f};
        float[] alaBase = varianteAzul ? new float[] {0.48f, 0.68f, 1.00f} : new float[] {0.98f, 0.82f, 0.26f};
        float[] borde = varianteAzul ? new float[] {0.18f, 0.25f, 0.50f} : new float[] {0.22f, 0.13f, 0.17f};

        Sprite s = new Sprite();
        int y0 = 5 + desplazamientoY;
        agregar(s, new int[][] {{4,y0},{5,y0},{6,y0},{7,y0}}, borde);
        agregar(s, new int[][] {{3,y0+1},{4,y0+1},{5,y0+1},{6,y0+1},{7,y0+1}}, alaBase);
        agregar(s, new int[][] {{4,y0+2},{5,y0+2},{6,y0+2}}, alaClaro);
        return s;
    }

    private void agregar(Sprite sprite, int[][] celdas, float[] color) {
        for (int[] c : celdas) {
            sprite.agregarPixel(c[0], c[1], color);
        }
    }
}
