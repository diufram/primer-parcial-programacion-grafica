package com.graphics;

import java.util.ArrayList;
import java.util.List;

public class ObjetoEscena {
    private final List<Parte> partes;
    private float x;
    private float y;
    private float rotacion;
    private float escala;
    private boolean visible;

    public ObjetoEscena() {
        this.partes = new ArrayList<>();
        this.escala = 1.0f;
        this.visible = true;
    }

    public ObjetoEscena agregarParte(Parte parte) {
        partes.add(parte);
        return this;
    }

    public List<Parte> getPartes() {
        return partes;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getRotacion() {
        return rotacion;
    }

    public void setRotacion(float rotacion) {
        this.rotacion = rotacion;
    }

    public float getEscala() {
        return escala;
    }

    public void setEscala(float escala) {
        this.escala = escala;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
