package com.graphics;

public class GeneradorFondo {

    private final Renderizador renderizador;

    public GeneradorFondo(Renderizador renderizador) {
        this.renderizador = renderizador;
    }

    public void dibujar() {
        dibujarCielo();
        dibujarNubes();
        dibujarSueloPixelado();
    }

    private void dibujarCielo() {
        // Cielo estilo Flappy Bird: celeste claro con leve degradado.
        int franjas = 16;

        float[] cieloArriba = { 0.42f, 0.80f, 0.95f };
        float[] cieloAbajo = { 0.55f, 0.86f, 0.96f };

        for (int i = 0; i < franjas; i++) {
            float altoFranja = 2.0f / franjas;
            float y = -1.0f + altoFranja * i + altoFranja * 0.5f;

            float t = i / (float) (franjas - 1);

            float r = interpolar(cieloAbajo[0], cieloArriba[0], t);
            float g = interpolar(cieloAbajo[1], cieloArriba[1], t);
            float b = interpolar(cieloAbajo[2], cieloArriba[2], t);

            renderizador.dibujarRect(0.0f, y, 2.0f, altoFranja + 0.01f, r, g, b);
        }
    }

    private void dibujarNubes() {
        dibujarNubePixelada(-0.75f, 0.72f, 0.75f);
        dibujarNubePixelada(0.10f, 0.80f, 0.62f);
        dibujarNubePixelada(0.78f, 0.62f, 0.70f);
        dibujarNubePixelada(-0.25f, 0.50f, 0.55f);
    }

    private void dibujarNubePixelada(float x, float y, float escala) {
        float[] blanco = { 0.96f, 1.00f, 1.00f };
        float[] sombra = { 0.82f, 0.94f, 0.96f };

        float p = 0.055f * escala;

        // Sombra inferior para dar volumen pixelado.
        rect(x - p * 2.0f, y - p * 0.8f, p * 4.0f, p * 1.0f, sombra);
        rect(x + p * 1.8f, y - p * 0.8f, p * 2.4f, p * 1.0f, sombra);

        // Cuerpo principal de la nube.
        rect(x - p * 3.0f, y, p * 2.0f, p * 1.2f, blanco);
        rect(x - p * 1.7f, y + p * 0.45f, p * 2.2f, p * 1.8f, blanco);
        rect(x, y + p * 0.65f, p * 2.6f, p * 2.2f, blanco);
        rect(x + p * 2.0f, y + p * 0.25f, p * 2.4f, p * 1.5f, blanco);
        rect(x + p * 3.2f, y - p * 0.10f, p * 1.5f, p * 1.0f, blanco);

        // Base plana para que se vea estilo sprite.
        rect(x, y - p * 0.35f, p * 6.2f, p * 1.0f, blanco);
    }

    private void dibujarSueloPixelado() {
        float[] verdeOscuro = { 0.20f, 0.55f, 0.18f };
        float[] verdeBase = { 0.36f, 0.78f, 0.22f };
        float[] verdeClaro = { 0.65f, 0.96f, 0.34f };

        float[] tierraBase = { 0.72f, 0.52f, 0.25f };
        float[] tierraClara = { 0.86f, 0.66f, 0.34f };
        float[] tierraOscura = { 0.55f, 0.36f, 0.18f };

        // Zona de tierra.
        renderizador.dibujarRect(
                0.0f,
                Constantes.Y_SUELO,
                2.0f,
                0.40f,
                tierraBase[0],
                tierraBase[1],
                tierraBase[2]);

        // Borde oscuro superior del suelo.
        renderizador.dibujarRect(
                0.0f,
                Constantes.Y_CESPED - 0.015f,
                2.0f,
                0.035f,
                verdeOscuro[0],
                verdeOscuro[1],
                verdeOscuro[2]);

        // Césped principal.
        renderizador.dibujarRect(
                0.0f,
                Constantes.Y_CESPED + 0.02f,
                2.0f,
                0.065f,
                verdeBase[0],
                verdeBase[1],
                verdeBase[2]);

        // Brillo del césped.
        renderizador.dibujarRect(
                0.0f,
                Constantes.Y_CESPED + 0.055f,
                2.0f,
                0.018f,
                verdeClaro[0],
                verdeClaro[1],
                verdeClaro[2]);

        // Patrón pixelado/checker de la tierra.
        float inicioX = -1.05f;
        float tileW = 0.12f;
        float tileH = 0.075f;

        for (int fila = 0; fila < 5; fila++) {
            for (int col = 0; col < 20; col++) {
                float x = inicioX + col * tileW;
                float y = Constantes.Y_SUELO + 0.12f - fila * tileH;

                boolean alternar = (fila + col) % 2 == 0;

                if (alternar) {
                    rect(x, y, tileW * 0.85f, tileH * 0.75f, tierraClara);
                } else {
                    rect(x, y, tileW * 0.85f, tileH * 0.75f, tierraOscura);
                }
            }
        }

        // Pequeños bloques verdes arriba para efecto pasto.
        for (int i = 0; i < 18; i++) {
            float x = -1.0f + i * 0.12f;
            float altura = (i % 2 == 0) ? 0.035f : 0.022f;

            rect(
                    x,
                    Constantes.Y_CESPED + 0.085f,
                    0.055f,
                    altura,
                    verdeClaro);
        }
    }

    private void rect(float x, float y, float ancho, float alto, float[] color) {
        renderizador.dibujarRect(
                x,
                y,
                ancho,
                alto,
                color[0],
                color[1],
                color[2]);
    }

    private float interpolar(float a, float b, float t) {
        return a + (b - a) * t;
    }
}