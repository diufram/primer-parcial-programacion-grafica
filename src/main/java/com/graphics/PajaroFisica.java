package com.graphics;

public class PajaroFisica {
    private float x;
    private float y;
    private float velocidadY;
    private final float gravedad;
    private final float impulsoSalto;
    private final float velocidadMaxCaida;
    private final float ancho;
    private final float alto;
    private boolean vivo;

    public PajaroFisica(float x, float y, float gravedad, float impulsoSalto, float velocidadMaxCaida,
                        float ancho, float alto) {
        this.x = x;
        this.y = y;
        this.gravedad = gravedad;
        this.impulsoSalto = impulsoSalto;
        this.velocidadMaxCaida = velocidadMaxCaida;
        this.ancho = ancho;
        this.alto = alto;
        this.velocidadY = 0f;
        this.vivo = true;
    }

    public void saltar() {
        if (vivo) {
            velocidadY = impulsoSalto;
        }
    }

    public void actualizar(float dt) {
        if (!vivo) {
            return;
        }
        velocidadY += gravedad * dt;
        if (velocidadY < velocidadMaxCaida) {
            velocidadY = velocidadMaxCaida;
        }
        y += velocidadY * dt;
    }

    public boolean colisionaSuelo() {
        return y - (alto * 0.5f) <= -1.0f;
    }

    public boolean colisionaTecho() {
        return y + (alto * 0.5f) >= 1.0f;
    }

    public void reiniciar(float nuevoY) {
        y = nuevoY;
        velocidadY = 0f;
        vivo = true;
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getAncho() { return ancho; }
    public float getAlto() { return alto; }
    public boolean estaVivo() { return vivo; }
    public void setVivo(boolean vivo) { this.vivo = vivo; }
}
