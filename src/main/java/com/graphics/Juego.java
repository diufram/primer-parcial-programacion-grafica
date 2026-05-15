package com.graphics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Juego {
    public static final float GRAVEDAD = -1.9f;
    public static final float IMPULSO_SALTO = 0.85f;
    public static final float VELOCIDAD_MAX_CAIDA = -1.8f;
    public static final float BIRD_ANCHO = 0.10f;
    public static final float BIRD_ALTO = 0.10f;
    public static final float BIRD_X_PLAYER1 = -0.45f;
    public static final float BIRD_X_PLAYER2 = -0.30f;

    private final List<Tuberia> tuberias;
    private final Random aleatorio;
    private final float gapMinCentro;
    private final float gapMaxCentro;
    private final float pipeAncho;
    private final float gapAlto;

    private Pajaro jugador1;
    private Pajaro jugador2;
    private int puntajeJugador1;
    private int puntajeJugador2;
    private float temporizadorSpawn;
    private float velocidadTuberias;
    private float tiempoEntreTuberias;
    private int nivel;
    private boolean iniciado;
    private boolean finJuego;
    private boolean juegoTerminado;

    private final int ubicacionOffset;
    private final int ubicacionEscala;
    private final int ubicacionColor;

    public Juego(int ubicacionOffset, int ubicacionEscala, int ubicacionColor) {
        this.tuberias = new ArrayList<>();
        this.aleatorio = new Random();
        this.gapMinCentro = -0.45f;
        this.gapMaxCentro = 0.45f;
        this.pipeAncho = 0.18f;
        this.gapAlto = 0.48f;
        this.ubicacionOffset = ubicacionOffset;
        this.ubicacionEscala = ubicacionEscala;
        this.ubicacionColor = ubicacionColor;
        reiniciar();
    }

    public void reiniciar() {
        jugador1 = new Pajaro(BIRD_X_PLAYER1, 0.0f, GRAVEDAD, IMPULSO_SALTO,
                           VELOCIDAD_MAX_CAIDA, BIRD_ANCHO, BIRD_ALTO,
                           new float[]{0.98f, 0.85f, 0.20f},
                           ubicacionOffset, ubicacionEscala, ubicacionColor);

        jugador2 = new Pajaro(BIRD_X_PLAYER2, 0.0f, GRAVEDAD, IMPULSO_SALTO,
                           VELOCIDAD_MAX_CAIDA, BIRD_ANCHO, BIRD_ALTO,
                           new float[]{0.25f, 0.55f, 0.95f},
                           ubicacionOffset, ubicacionEscala, ubicacionColor);

        puntajeJugador1 = 0;
        puntajeJugador2 = 0;
        temporizadorSpawn = 0.0f;
        velocidadTuberias = 0.62f;
        tiempoEntreTuberias = 1.5f;
        nivel = 1;
        iniciado = false;
        finJuego = false;
        juegoTerminado = false;
        tuberias.clear();
    }

    public void saltoJugador1() {
        if (!iniciado && !finJuego) {
            iniciado = true;
        }
        if (finJuego && !jugador1.estaVivo()) {
            reiniciar();
            iniciado = true;
        }
        jugador1.saltar();
    }

    public void saltoJugador2() {
        if (!iniciado && !finJuego) {
            iniciado = true;
        }
        if (finJuego && !jugador2.estaVivo()) {
            reiniciar();
            iniciado = true;
        }
        jugador2.saltar();
    }

    public void actualizar(float dt) {
        if (!iniciado || juegoTerminado) return;

        jugador1.actualizar(dt);
        jugador2.actualizar(dt);

        if (jugador1.estaVivo()) {
            if (jugador1.colisionaTecho()) {
                jugador1.setVivo(false);
            }
            if (jugador1.colisionaSuelo()) {
                jugador1.setVivo(false);
            }
        }

        if (jugador2.estaVivo()) {
            if (jugador2.colisionaTecho()) {
                jugador2.setVivo(false);
            }
            if (jugador2.colisionaSuelo()) {
                jugador2.setVivo(false);
            }
        }

        if (!jugador1.estaVivo() && !jugador2.estaVivo()) {
            finJuego = true;
            juegoTerminado = true;
            return;
        }

        temporizadorSpawn += dt;
        if (temporizadorSpawn >= tiempoEntreTuberias) {
            temporizadorSpawn = 0.0f;
            crearTuberia();
        }

        Iterator<Tuberia> it = tuberias.iterator();
        while (it.hasNext()) {
            Tuberia p = it.next();
            p.actualizar(velocidadTuberias, dt);

            if (p.pasoPorX(BIRD_X_PLAYER1) && jugador1.estaVivo()) {
                p.setPuntuada(true);
                puntajeJugador1++;
                actualizarDificultad();
            }
            if (p.pasoPorX(BIRD_X_PLAYER2) && jugador2.estaVivo()) {
                p.setPuntuada(true);
                puntajeJugador2++;
                actualizarDificultad();
            }

            if (jugador1.estaVivo() && colisionPajaroTuberia(jugador1, p)) {
                jugador1.setVivo(false);
            }
            if (jugador2.estaVivo() && colisionPajaroTuberia(jugador2, p)) {
                jugador2.setVivo(false);
            }

            if (p.estaFueraDePantalla()) {
                it.remove();
            }
        }
    }

    private boolean colisionPajaroTuberia(Pajaro pajaro, Tuberia tuberia) {
        float pajaroIzquierda = pajaro.getX() - (pajaro.getAncho() * 0.5f);
        float pajaroDerecha = pajaro.getX() + (pajaro.getAncho() * 0.5f);
        float pajaroAbajo = pajaro.getY() - (pajaro.getAlto() * 0.5f);
        float pajaroArriba = pajaro.getY() + (pajaro.getAlto() * 0.5f);

        float tuberiaIzquierda = tuberia.getX() - (tuberia.getAncho() * 0.5f);
        float tuberiaDerecha = tuberia.getX() + (tuberia.getAncho() * 0.5f);
        boolean haySolapamientoX = pajaroDerecha > tuberiaIzquierda && pajaroIzquierda < tuberiaDerecha;
        if (!haySolapamientoX) return false;

        return pajaroArriba > tuberia.getGapSuperior() || pajaroAbajo < tuberia.getGapInferior();
    }

    private void crearTuberia() {
        float gapCentro = gapMinCentro + aleatorio.nextFloat() * (gapMaxCentro - gapMinCentro);
        tuberias.add(new Tuberia(1.2f, gapCentro, pipeAncho, gapAlto,
                          ubicacionOffset, ubicacionEscala, ubicacionColor));
    }

    private void actualizarDificultad() {
        int puntajeMaximo = Math.max(puntajeJugador1, puntajeJugador2);
        nivel = (puntajeMaximo / 5) + 1;
        if (nivel > 10) nivel = 10;

        velocidadTuberias = 0.62f + (nivel - 1) * 0.08f;
        if (velocidadTuberias > 1.4f) velocidadTuberias = 1.4f;

        tiempoEntreTuberias = 1.5f - (nivel - 1) * 0.08f;
        if (tiempoEntreTuberias < 0.8f) tiempoEntreTuberias = 0.8f;
    }

    public void dibujarTuberias() {
        for (Tuberia p : tuberias) {
            p.dibujar(0.18f, 0.70f, 0.25f);
        }
    }

    public void dibujarJugadores() {
        jugador1.dibujar();
        jugador2.dibujar();
    }

    public int getPuntajeJugador1() { return puntajeJugador1; }
    public int getPuntajeJugador2() { return puntajeJugador2; }
    public boolean estaJuegoTerminado() { return juegoTerminado; }
    public int getNivel() { return nivel; }
}
