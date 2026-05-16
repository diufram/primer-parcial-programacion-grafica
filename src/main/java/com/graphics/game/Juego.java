package com.graphics.game;

import com.graphics.render.Escenario;
import com.graphics.audio.GestorAudio;
import com.graphics.objects.Pajaro;
import com.graphics.objects.Tuberia;
import com.graphics.objects.GameOver;
import com.graphics.objects.FondoOscuro;
import com.graphics.render.Constantes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Juego {
    private final List<Tuberia> tuberias;
    private final Random aleatorio;
    private final Escenario escenario;
    private final GestorAudio gestorAudio;

    private Pajaro jugador1;
    private Pajaro jugador2;
    private Pajaro jugador3;
    private GameOver gameOver;
    private FondoOscuro fondoOscuro;
    private int puntajeJugador1;
    private int puntajeJugador2;
    private int puntajeJugador3;
    private float temporizadorSpawn;
    private float velocidadTuberias;
    private float tiempoEntreTuberias;
    private int nivel;
    private boolean iniciado;
    private boolean finJuego;
    private boolean juegoTerminado;

    public Juego(Escenario escenario, GestorAudio gestorAudio, GameOver gameOver, FondoOscuro fondoOscuro) {
        this.tuberias = new ArrayList<>();
        this.aleatorio = new Random();
        this.escenario = escenario;
        this.gestorAudio = gestorAudio;
        this.fondoOscuro = fondoOscuro;
        this.gameOver = gameOver;
        reiniciar();
    }

    public void reiniciar() {
        limpiarTuberias();

        if (jugador1 == null) {
            jugador1 = new Pajaro(Constantes.BIRD_X_PLAYER1, 0.0f, Constantes.GRAVEDAD,
                    Constantes.IMPULSO_SALTO, Constantes.VELOCIDAD_MAX_CAIDA,
                    Constantes.BIRD_ANCHO, Constantes.BIRD_ALTO, Constantes.COLOR_JUGADOR1);
            escenario.agregarObjeto(jugador1);
        } else {
            jugador1.reiniciar(0.0f);
        }

        if (jugador2 == null) {
            jugador2 = new Pajaro(Constantes.BIRD_X_PLAYER2, 0.0f, Constantes.GRAVEDAD,
                    Constantes.IMPULSO_SALTO, Constantes.VELOCIDAD_MAX_CAIDA,
                    Constantes.BIRD_ANCHO, Constantes.BIRD_ALTO, Constantes.COLOR_JUGADOR2);
            escenario.agregarObjeto(jugador2);
        } else {
            jugador2.reiniciar(0.0f);
        }

        if (jugador3 == null) {
            jugador3 = new Pajaro(Constantes.BIRD_X_PLAYER3, 0.0f, Constantes.GRAVEDAD,
                    Constantes.IMPULSO_SALTO, Constantes.VELOCIDAD_MAX_CAIDA,
                    Constantes.BIRD_ANCHO, Constantes.BIRD_ALTO, Constantes.COLOR_JUGADOR3);
            escenario.agregarObjeto(jugador3);
        } else {
            jugador3.reiniciar(0.0f);
        }

        puntajeJugador1 = 0;
        puntajeJugador2 = 0;
        puntajeJugador3 = 0;
        temporizadorSpawn = 0.0f;
        velocidadTuberias = Constantes.VELOCIDAD_TUBERIAS_INICIAL;
        tiempoEntreTuberias = Constantes.TIEMPO_ENTRE_TUBERIAS_INICIAL;
        nivel = 1;
        iniciado = false;
        finJuego = false;
        juegoTerminado = false;
        gameOver.setVisible(false);
        gameOver.setAlpha(0.0f);
        fondoOscuro.setVisible(false);
        fondoOscuro.setAlpha(0.0f);
    }

    public void saltoJugador1() {
        if (!iniciado && !finJuego) {
            iniciado = true;
        }
        if (finJuego && !jugador1.estaVivo()) {
            reiniciar();
            iniciado = true;
            gestorAudio.reproducirTransicion();
        }
        jugador1.saltar();
        gestorAudio.reproducirSalto();
    }

    public void saltoJugador2() {
        if (!iniciado && !finJuego) {
            iniciado = true;
        }
        if (finJuego && !jugador2.estaVivo()) {
            reiniciar();
            iniciado = true;
            gestorAudio.reproducirTransicion();
        }
        jugador2.saltar();
        gestorAudio.reproducirSalto();
    }

    public void saltoJugador3() {
        if (!iniciado && !finJuego) {
            iniciado = true;
        }
        if (finJuego && !jugador3.estaVivo()) {
            reiniciar();
            iniciado = true;
            gestorAudio.reproducirTransicion();
        }
        jugador3.saltar();
        gestorAudio.reproducirSalto();
    }

    public void actualizar(float dt) {
        if (!iniciado || juegoTerminado)
            return;

        jugador1.actualizar(dt);
        jugador2.actualizar(dt);
        jugador3.actualizar(dt);

        if (jugador1.estaVivo()) {
            if (jugador1.colisionaTecho()) {
                jugador1.setVivo(false);
                gestorAudio.reproducirGolpe();
            }
            if (jugador1.colisionaSuelo()) {
                jugador1.setVivo(false);
                gestorAudio.reproducirGolpe();
            }
        }

        if (jugador2.estaVivo()) {
            if (jugador2.colisionaTecho()) {
                jugador2.setVivo(false);
                gestorAudio.reproducirGolpe();
            }
            if (jugador2.colisionaSuelo()) {
                jugador2.setVivo(false);
                gestorAudio.reproducirGolpe();
            }
        }

        if (jugador3.estaVivo()) {
            if (jugador3.colisionaTecho()) {
                jugador3.setVivo(false);
                gestorAudio.reproducirGolpe();
            }
            if (jugador3.colisionaSuelo()) {
                jugador3.setVivo(false);
                gestorAudio.reproducirGolpe();
            }
        }

        if (!jugador1.estaVivo() && !jugador2.estaVivo() && !jugador3.estaVivo()) {
            finJuego = true;
            juegoTerminado = true;
            fondoOscuro.setVisible(true);
            gameOver.setVisible(true);
            fondoOscuro.setAlpha(Constantes.ALPHA_FONDO_OSCURO);
            gameOver.setAlpha(1.0f);
            gestorAudio.reproducirMuerte();
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
            p.actualizar(dt);

            if (p.pasoPorX(Constantes.BIRD_X_PLAYER1, 1) && jugador1.estaVivo()) {
                puntajeJugador1++;
                gestorAudio.reproducirPunto();
                actualizarDificultad();
            }
            if (p.pasoPorX(Constantes.BIRD_X_PLAYER2, 2) && jugador2.estaVivo()) {
                puntajeJugador2++;
                gestorAudio.reproducirPunto();
                actualizarDificultad();
            }
            if (p.pasoPorX(Constantes.BIRD_X_PLAYER3, 3) && jugador3.estaVivo()) {
                puntajeJugador3++;
                gestorAudio.reproducirPunto();
                actualizarDificultad();
            }

            if (jugador1.estaVivo() && colisionPajaroTuberia(jugador1, p)) {
                jugador1.setVivo(false);
                gestorAudio.reproducirGolpe();
            }
            if (jugador2.estaVivo() && colisionPajaroTuberia(jugador2, p)) {
                jugador2.setVivo(false);
                gestorAudio.reproducirGolpe();
            }
            if (jugador3.estaVivo() && colisionPajaroTuberia(jugador3, p)) {
                jugador3.setVivo(false);
                gestorAudio.reproducirGolpe();
            }

            if (p.estaFueraDePantalla()) {
                escenario.quitarObjeto(p);
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
        if (!haySolapamientoX)
            return false;

        return pajaroArriba > tuberia.getGapSuperior() || pajaroAbajo < tuberia.getGapInferior();
    }

    private void crearTuberia() {
        float gapCentro = Constantes.GAP_MIN_CENTRO
                + aleatorio.nextFloat() * (Constantes.GAP_MAX_CENTRO - Constantes.GAP_MIN_CENTRO);
        Tuberia tuberia = new Tuberia(1.2f, gapCentro, Constantes.PIPE_ANCHO, Constantes.GAP_ALTO);
        tuberia.setVelocidad(velocidadTuberias);
        tuberias.add(tuberia);
        escenario.agregarObjeto(tuberia);
    }

    private void limpiarTuberias() {
        for (Tuberia tuberia : tuberias) {
            escenario.quitarObjeto(tuberia);
        }
        tuberias.clear();
    }

    private void actualizarDificultad() {
        int puntajeMaximo = Math.max(puntajeJugador1, Math.max(puntajeJugador2, puntajeJugador3));
        nivel = (puntajeMaximo / 5) + 1;
        if (nivel > 10)
            nivel = 10;

        velocidadTuberias = Constantes.VELOCIDAD_TUBERIAS_INICIAL + (nivel - 1) * 0.08f;
        if (velocidadTuberias > Constantes.VELOCIDAD_TUBERIAS_MAX)
            velocidadTuberias = Constantes.VELOCIDAD_TUBERIAS_MAX;

        tiempoEntreTuberias = Constantes.TIEMPO_ENTRE_TUBERIAS_INICIAL - (nivel - 1) * 0.08f;
        if (tiempoEntreTuberias < Constantes.TIEMPO_ENTRE_TUBERIAS_MIN)
            tiempoEntreTuberias = Constantes.TIEMPO_ENTRE_TUBERIAS_MIN;

        for (Tuberia tuberia : tuberias) {
            tuberia.setVelocidad(velocidadTuberias);
        }
    }

    public int getPuntajeJugador1() {
        return puntajeJugador1;
    }

    public int getPuntajeJugador2() {
        return puntajeJugador2;
    }

    public int getPuntajeJugador3() {
        return puntajeJugador3;
    }

    public boolean estaJuegoTerminado() {
        return juegoTerminado;
    }

    public int getNivel() {
        return nivel;
    }
}
