package com.graphics;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

public class AppFlappyBird {

    private long ventana;
    private Renderizador renderizador;
    private GeneradorFondo generadorFondo;
    private GeneradorUI generadorUI;
    private Juego juego;
    private Entrada entrada;
    private Sonido sonido;

    private static final int ESTADO_INICIO = 0;
    private static final int ESTADO_JUGANDO = 1;
    private static final int ESTADO_GAME_OVER = 2;

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
        generadorFondo = new GeneradorFondo(renderizador);
        generadorUI = new GeneradorUI(renderizador);
        juego = new Juego(
            renderizador.getUbicacionOffset(),
            renderizador.getUbicacionEscala(),
            renderizador.getUbicacionColor()
        );
        entrada = new Entrada(ventana);
        sonido = new Sonido();
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
        if (entrada.estaPresionadoEscape()) {
            GLFW.glfwSetWindowShouldClose(ventana, true);
            return;
        }

        int estado = generadorUI.getEstadoJuego();

        if (estado == ESTADO_INICIO) {
            if (entrada.sePresionoEspacio() || entrada.sePresionoEnter()) {
                iniciarJuego();
            } else if (entrada.saltoJugador2()) {
                iniciarJuego();
            }
        } else if (estado == ESTADO_JUGANDO) {
            if (entrada.sePresionoEspacio()) {
                juego.saltoJugador1();
                sonido.reproducirSalto();
            }
            if (entrada.saltoJugador2()) {
                juego.saltoJugador2();
                sonido.reproducirSalto();
            }
        } else if (estado == ESTADO_GAME_OVER) {
            if (entrada.sePresionoEspacio() || entrada.sePresionoEnter() || entrada.sePresionoR()) {
                reiniciarJuego();
            }
        }
    }

    private void iniciarJuego() {
        generadorUI.establecerEstado(ESTADO_JUGANDO);
        juego.saltoJugador1();
        sonido.reproducirSalto();
    }

    private void reiniciarJuego() {
        juego.reiniciar();
        generadorUI.establecerEstado(ESTADO_INICIO);
        generadorUI.establecerPuntajes(0, 0);
        generadorUI.establecerNivel(1);
    }

    private void actualizar(float dt) {
        generadorUI.actualizar(dt);
        if (generadorUI.getEstadoJuego() != ESTADO_JUGANDO) return;

        juego.actualizar(dt);

        generadorUI.establecerPuntajes(juego.getPuntajeJugador1(), juego.getPuntajeJugador2());
        generadorUI.establecerNivel(juego.getNivel());

        if (juego.estaJuegoTerminado()) {
            generadorUI.establecerEstado(ESTADO_GAME_OVER);
            generadorUI.establecerJuegoTerminado(true);
            sonido.reproducirFinJuego();
        }

        actualizarTitulo();
    }

    private void renderizar() {
        renderizador.usarPrograma();
        generadorFondo.dibujar();
        juego.dibujarTuberias();
        juego.dibujarJugadores();
        generadorUI.dibujar();
    }

    private void actualizarTitulo() {
        String titulo = "Flappy Bird 2P | P1:" + juego.getPuntajeJugador1()
            + " P2:" + juego.getPuntajeJugador2()
            + " | Nivel:" + juego.getNivel();

        int estado = generadorUI.getEstadoJuego();
        if (estado == ESTADO_INICIO) {
            titulo += " | SPACE/W para jugar";
        } else if (estado == ESTADO_GAME_OVER) {
            titulo += " | GAME OVER";
        }

        GLFW.glfwSetWindowTitle(ventana, titulo);
    }

    private void limpiar() {
        sonido.limpiar();
        renderizador.limpiar();
        GLFW.glfwDestroyWindow(ventana);
        GLFW.glfwTerminate();
    }

    public static void main(String[] args) {
        new AppFlappyBird().ejecutar();
    }
}