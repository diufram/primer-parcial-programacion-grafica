package com.graphics;

import org.lwjgl.glfw.GLFW;

public class Entrada {
    private final long ventana;
    private boolean espacioAnterior;
    private boolean wAnterior;
    private boolean arribaAnterior;
    private boolean rAnterior;
    private boolean enterAnterior;

    public Entrada(long ventana) {
        this.ventana = ventana;
        this.espacioAnterior = false;
        this.wAnterior = false;
        this.arribaAnterior = false;
        this.rAnterior = false;
        this.enterAnterior = false;
    }

    public boolean sePresionoEspacio() {
        boolean ahora = GLFW.glfwGetKey(ventana, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS;
        boolean resultado = ahora && !espacioAnterior;
        espacioAnterior = ahora;
        return resultado;
    }

    public boolean sePresionoW() {
        boolean ahora = GLFW.glfwGetKey(ventana, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS;
        boolean resultado = ahora && !wAnterior;
        wAnterior = ahora;
        return resultado;
    }

    public boolean sePresionoArriba() {
        boolean ahora = GLFW.glfwGetKey(ventana, GLFW.GLFW_KEY_UP) == GLFW.GLFW_PRESS;
        boolean resultado = ahora && !arribaAnterior;
        arribaAnterior = ahora;
        return resultado;
    }

    public boolean sePresionoR() {
        boolean ahora = GLFW.glfwGetKey(ventana, GLFW.GLFW_KEY_R) == GLFW.GLFW_PRESS;
        boolean resultado = ahora && !rAnterior;
        rAnterior = ahora;
        return resultado;
    }

    public boolean sePresionoEnter() {
        boolean ahora = GLFW.glfwGetKey(ventana, GLFW.GLFW_KEY_ENTER) == GLFW.GLFW_PRESS;
        boolean resultado = ahora && !enterAnterior;
        enterAnterior = ahora;
        return resultado;
    }

    public boolean estaPresionadoEscape() {
        return GLFW.glfwGetKey(ventana, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS;
    }

    public boolean saltoJugador2() {
        return sePresionoW() || sePresionoArriba();
    }
}
