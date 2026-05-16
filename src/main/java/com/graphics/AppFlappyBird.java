package com.graphics;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

public class AppFlappyBird {

    private long ventana;
    private Renderizador renderizador;
    private Escenario escenario;
    private Juego juego;
    private GestorAudio gestorAudio;
    private boolean espacioAnterior;
    private boolean wAnterior;
    private boolean arribaAnterior;
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
            "Flappy Bird - 2 Jugadores",
            0, 0
        );

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
        new GeneradorFondo(escenario);
        juego = new Juego(escenario, gestorAudio);
    }

    private void bucleJuego() {
        float ultimoTiempo = (float) GLFW.glfwGetTime();
        while (!GLFW.glfwWindowShouldClose(ventana)) {
            float ahora = (float) GLFW.glfwGetTime();
            float dt = ahora - ultimoTiempo;
            ultimoTiempo = ahora;
            if (dt > 0.033f) dt = 0.033f;

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

    private void renderizar() {
        renderizador.prepararFrame();
        escenario.dibujar(renderizador);
    }

    private void actualizarTitulo() {
        String titulo = "Escenario Poligonal 2P | P1:" + juego.getPuntajeJugador1()
            + " P2:" + juego.getPuntajeJugador2()
            + " | Nivel:" + juego.getNivel();

        if (juego.estaJuegoTerminado()) {
            titulo += " | GAME OVER | SPACE/ENTER/R reinician";
        } else {
            titulo += " | SPACE P1 | W/UP P2";
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
