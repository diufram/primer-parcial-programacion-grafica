package com.graphics;

public class Pajaro {
    private final PajaroFisica fisica;
    private final PajaroVisual visual;

    public Pajaro(float x, float y, float gravedad, float impulsoSalto, float velocidadMaxCaida,
                  float ancho, float alto, float[] color, Renderizador renderizador) {
        this.fisica = new PajaroFisica(x, y, gravedad, impulsoSalto, velocidadMaxCaida, ancho, alto);
        this.visual = new PajaroVisual(renderizador, ancho, alto, color);
    }

    public void saltar() {
        fisica.saltar();
        visual.onSaltar();
    }

    public void actualizar(float dt) {
        fisica.actualizar(dt);
        visual.actualizar(dt);
    }

    public void dibujar() {
        visual.dibujar(fisica.getX(), fisica.getY());
    }

    public boolean estaVivo() { return fisica.estaVivo(); }
    public void setVivo(boolean vivo) { fisica.setVivo(vivo); }
    public float getX() { return fisica.getX(); }
    public float getY() { return fisica.getY(); }
    public float getAncho() { return fisica.getAncho(); }
    public float getAlto() { return fisica.getAlto(); }
    public boolean colisionaSuelo() { return fisica.colisionaSuelo(); }
    public boolean colisionaTecho() { return fisica.colisionaTecho(); }

    public void reiniciar(float nuevoY) {
        fisica.reiniciar(nuevoY);
        visual.reiniciar();
    }
}
