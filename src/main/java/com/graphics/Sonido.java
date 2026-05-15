package com.graphics;

public class Sonido {
    private boolean habilitado;

    public Sonido() {
        this.habilitado = true;
    }

    public void reproducirSalto() {
        if (habilitado) {
            System.out.println("[Sonido] Salto");
        }
    }

    public void reproducirFinJuego() {
        if (habilitado) {
            System.out.println("[Sonido] Fin de juego");
        }
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public boolean estaHabilitado() {
        return habilitado;
    }

    public void limpiar() {
        // Reservado para recursos de audio futuros.
    }
}
