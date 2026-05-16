package com.graphics.core;

import java.util.ArrayList;
import java.util.List;

public abstract class Objeto {
    protected float x;
    protected float y;
    protected float rotacion;
    protected float escala;
    protected boolean visible;
    protected final List<Parte> partes;

    public Objeto() {
        this.partes = new ArrayList<>();
        this.escala = 1.0f;
        this.visible = true;
    }

    public void agregarParte(Parte parte) {
        partes.add(parte);
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

    public void actualizar(float dt) {
    }
}