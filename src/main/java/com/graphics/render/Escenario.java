package com.graphics.render;

import com.graphics.core.Objeto;

import java.util.ArrayList;
import java.util.List;

public class Escenario {
    private final List<Objeto> objetos;

    public Escenario() {
        this.objetos = new ArrayList<>();
    }

    public void agregarObjeto(Objeto objeto) {
        objetos.add(objeto);
    }

    public void quitarObjeto(Objeto objeto) {
        objetos.remove(objeto);
    }

    public void dibujar(Renderizador renderizador) {
        for (Objeto objeto : objetos) {
            if (objeto.isVisible()) {
                renderizador.dibujarObjeto(objeto);
            }
        }
    }
}