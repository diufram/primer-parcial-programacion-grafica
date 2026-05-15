package com.graphics;

public class GeneradorUI {

    private final Renderizador renderizador;
    private float tiempoInterfaz;
    private int estadoJuego;
    private int puntajeJugador1;
    private int puntajeJugador2;
    private int nivel;
    private boolean juegoTerminado;

    private static final int ESTADO_INICIO = 0;
    private static final int ESTADO_JUGANDO = 1;
    private static final int ESTADO_GAME_OVER = 2;

    public GeneradorUI(Renderizador renderizador) {
        this.renderizador = renderizador;
        this.tiempoInterfaz = 0f;
        this.estadoJuego = ESTADO_INICIO;
        this.puntajeJugador1 = 0;
        this.puntajeJugador2 = 0;
        this.nivel = 1;
        this.juegoTerminado = false;
    }

    public void actualizar(float dt) {
        tiempoInterfaz += dt;
    }

    public void dibujar() {
        if (estadoJuego == ESTADO_INICIO) {
            dibujarPantallaInicio();
        } else if (estadoJuego == ESTADO_JUGANDO || estadoJuego == ESTADO_GAME_OVER) {
            dibujarHUD();
            if (estadoJuego == ESTADO_GAME_OVER) {
                dibujarPantallaGameOver();
            }
        }
    }

    public void establecerEstado(int estado) {
        this.estadoJuego = estado;
    }

    public void establecerPuntajes(int p1, int p2) {
        this.puntajeJugador1 = p1;
        this.puntajeJugador2 = p2;
    }

    public void establecerNivel(int nivel) {
        this.nivel = nivel;
    }

    public void establecerJuegoTerminado(boolean termino) {
        this.juegoTerminado = termino;
    }

    private void dibujarPantallaInicio() {
        float pulso = 1.0f + (float) Math.sin(tiempoInterfaz * Constantes.VELOCIDAD_PULSO_UI) * Constantes.AMPLITUD_PULSO_UI;

        dibujarPanel(0.0f, 0.42f, 1.72f, 0.46f, Constantes.COLOR_PANEL_TITULO);
        dibujarPanel(0.0f, 0.08f, 1.72f, 0.22f, Constantes.COLOR_PANEL_MEDIA);
        dibujarPanel(0.0f, Constantes.Y_PANEL_INFERIOR, 1.72f, 0.13f, Constantes.COLOR_PANEL_INFERIOR);

        float escalaTitulo = 1.35f;
        float escalaSubtitulo = 1.0f;

        renderizador.dibujarTextoConSombraCentrado(Constantes.TEXTO_TITULO, 0.0f, 0.56f, escalaTitulo, 1.0f, 0.95f, 0.2f);
        renderizador.dibujarTextoConSombraCentrado(Constantes.TEXTO_2_JUGADORES, 0.0f, 0.44f, escalaSubtitulo, 0.82f, 0.92f, 1.0f);

        float escalaControles = 0.95f;
        renderizador.dibujarTextoConSombra(Constantes.TEXTO_P1, -0.70f, 0.10f, escalaControles, 1.0f, 0.9f, 0.25f);
        renderizador.dibujarTextoConSombra(Constantes.TEXTO_P2, 0.06f, 0.10f, escalaControles, 0.25f, 0.85f, 1.0f);

        renderizador.dibujarTextoConSombraCentrado(Constantes.TEXTO_ENTER, 0.0f, -0.19f, pulso, 0.95f, 0.95f, 0.95f);
    }

    private void dibujarPantallaGameOver() {
        dibujarPanel(0.0f, 0.42f, 1.62f, 0.24f, Constantes.COLOR_GAME_OVER);
        dibujarPanel(0.0f, 0.11f, 1.62f, 0.24f, Constantes.COLOR_PANEL_MEDIA);
        dibujarPanel(0.0f, -0.22f, 1.62f, 0.13f, Constantes.COLOR_PANEL_INFERIOR);

        float escala = 1.25f;
        renderizador.dibujarTextoConSombraCentrado(Constantes.TEXTO_GAME_OVER, 0.0f, 0.50f, escala, 1.0f, 0.34f, 0.34f);

        float escalaPuntaje = 1.05f;
        renderizador.dibujarTextoConSombra("P1: " + puntajeJugador1, -0.70f, 0.24f, escalaPuntaje, 1.0f, 0.90f, 0.25f);
        renderizador.dibujarTextoConSombra("P2: " + puntajeJugador2, 0.05f, 0.24f, escalaPuntaje, 0.25f, 0.85f, 1.0f);

        String textoGanador;
        float[] colorGanador;

        if (puntajeJugador1 > puntajeJugador2) {
            textoGanador = Constantes.TEXTO_GANA_P1;
            colorGanador = Constantes.COLOR_GANA_P1;
        } else if (puntajeJugador2 > puntajeJugador1) {
            textoGanador = Constantes.TEXTO_GANA_P2;
            colorGanador = Constantes.COLOR_GANA_P2;
        } else {
            textoGanador = Constantes.TEXTO_EMPATE;
            colorGanador = new float[]{0.95f, 0.95f, 0.25f};
        }

        renderizador.dibujarTextoConSombraCentrado(textoGanador, 0.0f, 0.06f, escalaPuntaje, colorGanador[0], colorGanador[1], colorGanador[2]);
        renderizador.dibujarTextoConSombraCentrado(Constantes.TEXTO_REINICIAR, 0.0f, -0.22f, 0.9f, 0.92f, 0.92f, 0.92f);
    }

    private void dibujarHUD() {
        dibujarPanel(-0.52f, 0.88f, 0.96f, 0.14f, new float[]{0.05f, 0.08f, 0.12f});
        dibujarPanel(0.44f, 0.88f, 0.34f, 0.14f, new float[]{0.05f, 0.08f, 0.12f});

        float escalaHUD = 1.0f;
        float[] colorP1 = Constantes.COLOR_JUGADOR1;
        float[] colorP2 = Constantes.COLOR_JUGADOR2;

        renderizador.dibujarTextoConSombra("P1:" + puntajeJugador1, -0.95f, 0.83f, escalaHUD, colorP1[0], colorP1[1], colorP1[2]);
        renderizador.dibujarTextoConSombra("P2:" + puntajeJugador2, -0.48f, 0.83f, escalaHUD, colorP2[0], colorP2[1], colorP2[2]);
        renderizador.dibujarTextoConSombra("LV:" + nivel, 0.27f, 0.83f, escalaHUD, 1.0f, 1.0f, 1.0f);
    }

    private void dibujarPanel(float x, float y, float ancho, float alto, float[] color) {
        renderizador.dibujarRect(x, y, ancho, alto, color[0], color[1], color[2]);
    }

    public int getEstadoJuego() {
        return estadoJuego;
    }
}