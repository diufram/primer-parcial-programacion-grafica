package com.graphics;

import java.util.ArrayList;
import java.util.List;

public class Escenario {
    private final List<ObjetoEscena> objetos;

    public Escenario() {
        this.objetos = new ArrayList<>();
    }

    public void agregarObjeto(ObjetoEscena objeto) {
        objetos.add(objeto);
    }

    public void quitarObjeto(ObjetoEscena objeto) {
        objetos.remove(objeto);
    }

    public void dibujar(Renderizador renderizador) {
        for (ObjetoEscena objeto : objetos) {
            if (objeto.isVisible()) {
                renderizador.dibujarObjeto(objeto);
            }
        }
    }
}
