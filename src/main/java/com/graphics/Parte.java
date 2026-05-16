package com.graphics;

import java.util.ArrayList;
import java.util.List;

public class Parte {
    private final List<Punto> puntos;
    private final List<Parte> subpartes;
    private float x;
    private float y;
    private float pivoteX;
    private float pivoteY;
    private float rotacion;
    private float escala;
    private float[] color;
    private boolean visibleConTextura;
    private boolean visibleSinTextura;

    public Parte(float[] color) {
        this.puntos = new ArrayList<>();
        this.subpartes = new ArrayList<>();
        this.color = color;
        this.escala = 1.0f;
        this.visibleConTextura = true;
        this.visibleSinTextura = true;
    }

    public Parte agregarPunto(float x, float y) {
        puntos.add(new Punto(x, y));
        return this;
    }

    public Parte agregarSubparte(Parte parte) {
        subpartes.add(parte);
        return this;
    }

    public List<Punto> getPuntos() {
        return puntos;
    }

    public List<Parte> getSubpartes() {
        return subpartes;
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

    public float getPivoteX() {
        return pivoteX;
    }

    public void setPivoteX(float pivoteX) {
        this.pivoteX = pivoteX;
    }

    public float getPivoteY() {
        return pivoteY;
    }

    public void setPivoteY(float pivoteY) {
        this.pivoteY = pivoteY;
    }

    public void setPivote(float pivoteX, float pivoteY) {
        this.pivoteX = pivoteX;
        this.pivoteY = pivoteY;
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

    public float[] getColor() {
        return color;
    }

    public void setColor(float[] color) {
        this.color = color;
    }

    public boolean isVisibleConTextura() {
        return visibleConTextura;
    }

    public void setVisibleConTextura(boolean visibleConTextura) {
        this.visibleConTextura = visibleConTextura;
    }

    public boolean isVisibleSinTextura() {
        return visibleSinTextura;
    }

    public void setVisibleSinTextura(boolean visibleSinTextura) {
        this.visibleSinTextura = visibleSinTextura;
    }
}
