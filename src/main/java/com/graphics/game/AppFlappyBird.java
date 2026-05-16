package com.graphics.game;

import com.graphics.render.Renderizador;
import com.graphics.render.Constantes;
import com.graphics.audio.GestorAudio;
import com.graphics.core.Escenario;
import com.graphics.objects.Cielo;
import com.graphics.objects.Suelo;
import com.graphics.objects.Nube;
import com.graphics.objects.GameOver;
import com.graphics.objects.FondoOscuro;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

public class AppFlappyBird {

    private long ventana;
    private Renderizador renderizador;
    private Escenario escenario;
    private Juego juego;
    private GestorAudio gestorAudio;
    private GameOver gameOver;
    private FondoOscuro fondoOscuro;
    private boolean espacioAnterior;
    private boolean wAnterior;
    private boolean arribaAnterior;
    private boolean iAnterior;
    private boolean rAnterior;
    private boolean enterAnterior;
    private boolean tabAnterior;

    public void ejecutar() {
        inicializar();
        bucleJuego();
        limpiar();
    }

    private void inicializar() {
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("No se pudo iniciar GLFW");
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);

        ventana = GLFW.glfwCreateWindow(
                Constantes.ANCHO_VENTANA,
                Constantes.ALTO_VENTANA,
                "Flappy Bird - 3 Jugadores",
                0, 0);

        if (ventana == 0) {
            throw new RuntimeException("No se pudo crear la ventana");
        }

        GLFW.glfwMakeContextCurrent(ventana);
        GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(ventana);
        GL.createCapabilities();

        renderizador = new Renderizador();
        gestorAudio = new GestorAudio();
        escenario = new Escenario();
        gameOver = new GameOver();
        fondoOscuro = new FondoOscuro();
        gameOver.setVisible(false);
        fondoOscuro.setVisible(false);
        escenario.agregarObjeto(new Cielo());
        escenario.agregarObjeto(new Suelo());
        escenario.agregarObjeto(new Nube(-0.75f, 0.72f, 0.10f));
        escenario.agregarObjeto(new Nube(0.10f, 0.82f, 0.14f));
        escenario.agregarObjeto(new Nube(0.76f, 0.62f, 0.15f));
        escenario.agregarObjeto(new Nube(-0.18f, 0.54f, 0.12f));
        juego = new Juego(escenario, gestorAudio, gameOver, fondoOscuro);
    }

    private void renderizar() {
        renderizador.prepararFrame();
        escenario.dibujar(renderizador);
        if (juego.estaJuegoTerminado()) {
            renderizador.dibujarObjeto(fondoOscuro);
            renderizador.dibujarObjeto(gameOver);
        }
    }

    private void bucleJuego() {
        float ultimoTiempo = (float) GLFW.glfwGetTime();
        while (!GLFW.glfwWindowShouldClose(ventana)) {
            float ahora = (float) GLFW.glfwGetTime();
            float dt = ahora - ultimoTiempo;
            ultimoTiempo = ahora;
            if (dt > 0.033f)
                dt = 0.033f;

            procesarEntrada();
            actualizar(dt);
            renderizar();

            GLFW.glfwSwapBuffers(ventana);
            GLFW.glfwPollEvents();
        }
    }

    private void procesarEntrada() {
        if (GLFW.glfwGetKey(ventana, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS) {
            GLFW.glfwSetWindowShouldClose(ventana, true);
            return;
        }

        if (sePresiono(GLFW.GLFW_KEY_SPACE)) {
            if (juego.estaJuegoTerminado()) {
                reiniciarJuego();
            } else {
                juego.saltoJugador1();
            }
        }

        if (sePresiono(GLFW.GLFW_KEY_TAB)) {
            renderizador.alternarTexturas();
        }

        if (!juego.estaJuegoTerminado() && (sePresiono(GLFW.GLFW_KEY_W) || sePresiono(GLFW.GLFW_KEY_UP))) {
            juego.saltoJugador2();
        }

        // Tecla del tercer jugador.
        if (!juego.estaJuegoTerminado() && sePresiono(GLFW.GLFW_KEY_I)) {
            juego.saltoJugador3();
        }

        if (juego.estaJuegoTerminado() && (sePresiono(GLFW.GLFW_KEY_ENTER) || sePresiono(GLFW.GLFW_KEY_R))) {
            reiniciarJuego();
        }
    }

    private void reiniciarJuego() {
        juego.reiniciar();
        gestorAudio.reproducirTransicion();
    }

    private void actualizar(float dt) {
        juego.actualizar(dt);
        actualizarTitulo();
    }

    private void actualizarTitulo() {
        String titulo = "Escenario Poligonal 3P | P1:" + juego.getPuntajeJugador1()
                + " P2:" + juego.getPuntajeJugador2()
                + " P3:" + juego.getPuntajeJugador3()
                + " | Nivel:" + juego.getNivel();

        if (juego.estaJuegoTerminado()) {
            titulo += " | GAME OVER | SPACE/ENTER/R reinician";
        } else {
            titulo += " | SPACE P1 | W/UP P2 | I P3";
        }

        titulo += renderizador.isTexturasHabilitadas() ? " | TAB:texturas on" : " | TAB:texturas off";

        GLFW.glfwSetWindowTitle(ventana, titulo);
    }

    private void limpiar() {
        gestorAudio.limpiar();
        renderizador.limpiar();
        GLFW.glfwDestroyWindow(ventana);
        GLFW.glfwTerminate();
    }

    private boolean sePresiono(int tecla) {
        boolean ahora = GLFW.glfwGetKey(ventana, tecla) == GLFW.GLFW_PRESS;
        boolean anterior;

        if (tecla == GLFW.GLFW_KEY_SPACE) {
            anterior = espacioAnterior;
            espacioAnterior = ahora;
        } else if (tecla == GLFW.GLFW_KEY_W) {
            anterior = wAnterior;
            wAnterior = ahora;
        } else if (tecla == GLFW.GLFW_KEY_UP) {
            anterior = arribaAnterior;
            arribaAnterior = ahora;
        } else if (tecla == GLFW.GLFW_KEY_I) {
            anterior = iAnterior;
            iAnterior = ahora;
        } else if (tecla == GLFW.GLFW_KEY_R) {
            anterior = rAnterior;
            rAnterior = ahora;
        } else if (tecla == GLFW.GLFW_KEY_TAB) {
            anterior = tabAnterior;
            tabAnterior = ahora;
        } else {
            anterior = enterAnterior;
            enterAnterior = ahora;
        }

        return ahora && !anterior;
    }

    public static void main(String[] args) {
        new AppFlappyBird().ejecutar();
    }
}
