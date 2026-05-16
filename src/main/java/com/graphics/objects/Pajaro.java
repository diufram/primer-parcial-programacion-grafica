package com.graphics.objects;

import com.graphics.core.Objeto;
import com.graphics.core.Parte;
import com.graphics.render.Constantes;

public class Pajaro extends Objeto {
    private final float xInicial;
    private final float gravedad;
    private final float impulsoSalto;
    private final float velocidadMaxCaida;
    private final float ancho;
    private final float alto;
    private final Parte sprite;
    private final Parte alaRespaldo;
    private float velocidadY;
    private boolean vivo;
    private float tiempoAnimacion;

    public Pajaro(float x, float y, float gravedad, float impulsoSalto, float velocidadMaxCaida,
            float ancho, float alto, float[] color) {
        this.xInicial = x;
        this.y = y;
        this.gravedad = gravedad;
        this.impulsoSalto = impulsoSalto;
        this.velocidadMaxCaida = velocidadMaxCaida;
        this.ancho = ancho;
        this.alto = alto;
        this.vivo = true;
        this.sprite = crearSprite(color, ancho, alto);
        this.alaRespaldo = crearAla(color);

        agregarParte(crearCuerpo(color));
        agregarParte(alaRespaldo);
        agregarParte(crearPico());
        agregarParte(crearOjo());
        agregarParte(sprite);
        setX(x);
        setY(y);
    }

    public void saltar() {
        if (!vivo) {
            return;
        }
        velocidadY = impulsoSalto;
        alaRespaldo.setRotacion(-0.7f);
    }

    @Override
    public void actualizar(float dt) {
        if (!vivo) {
            return;
        }

        tiempoAnimacion += dt;
        velocidadY += gravedad * dt;
        if (velocidadY < velocidadMaxCaida) {
            velocidadY = velocidadMaxCaida;
        }
        y += velocidadY * dt;

        setX(xInicial);
        setY(y);
        setRotacion(Math.max(-0.45f, Math.min(0.35f, velocidadY * 0.35f)));
        float aleteo = (float) Math.sin(tiempoAnimacion * 12.0f) * 0.25f;
        alaRespaldo.setRotacion(alaRespaldo.getRotacion() * 0.85f + aleteo * 0.15f);
        sprite.setRutaTextura(obtenerFrameSprite());
    }

    public void reiniciar(float nuevoY) {
        this.y = nuevoY;
        this.velocidadY = 0.0f;
        this.vivo = true;
        this.tiempoAnimacion = 0.0f;
        setX(xInicial);
        setY(nuevoY);
        setRotacion(0.0f);
        alaRespaldo.setRotacion(0.0f);
        sprite.setRutaTextura(obtenerFrameSprite());
    }

    public boolean estaVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public float getAncho() {
        return ancho;
    }

    public float getAlto() {
        return alto;
    }

    public boolean colisionaSuelo() {
        return y - (alto * 0.5f) <= Constantes.LIMITE_ABAJO;
    }

    public boolean colisionaTecho() {
        return y + (alto * 0.5f) >= Constantes.LIMITE_ARRIBA;
    }

    private Parte crearSprite(float[] colorBase, float ancho, float alto) {
        Parte parte = new Parte(colorBase);
        parte.setRutaTextura(resolverRutaPajaro(colorBase, "midflap"));
        parte.setVisibleSinTextura(false);
        float medioAncho = ancho * 1.45f * 0.5f;
        float medioAlto = alto * 1.15f * 0.5f;
        parte.agregarPunto(-medioAncho, medioAlto)
                .agregarPunto(medioAncho, medioAlto)
                .agregarPunto(medioAncho, -medioAlto)
                .agregarPunto(-medioAncho, -medioAlto);
        parte.setPivote(0.0f, 0.0f);
        return parte;
    }

    private Parte crearCuerpo(float[] color) {
        Parte cuerpo = new Parte(color);
        cuerpo.setVisibleConTextura(false);
        cuerpo.agregarPunto(-0.045f, 0.000f)
                .agregarPunto(-0.028f, 0.028f)
                .agregarPunto(0.008f, 0.038f)
                .agregarPunto(0.040f, 0.018f)
                .agregarPunto(0.040f, -0.018f)
                .agregarPunto(0.008f, -0.038f)
                .agregarPunto(-0.028f, -0.028f);
        return cuerpo;
    }

    private Parte crearAla(float[] colorBase) {
        float[] colorAla = {
                Math.max(0.0f, colorBase[0] - 0.15f),
                Math.max(0.0f, colorBase[1] - 0.18f),
                Math.max(0.0f, colorBase[2] - 0.10f)
        };
        Parte parte = new Parte(colorAla);
        parte.setVisibleConTextura(false);
        parte.setX(-0.005f);
        parte.setY(-0.002f);
        parte.setPivote(-0.016f, 0.000f);
        parte.agregarPunto(-0.022f, 0.005f)
                .agregarPunto(0.000f, 0.022f)
                .agregarPunto(0.020f, 0.008f)
                .agregarPunto(0.010f, -0.028f)
                .agregarPunto(-0.018f, -0.018f);
        return parte;
    }

    private Parte crearPico() {
        Parte pico = new Parte(new float[] { 1.00f, 0.52f, 0.10f });
        pico.setVisibleConTextura(false);
        pico.agregarPunto(0.038f, 0.008f)
                .agregarPunto(0.065f, 0.000f)
                .agregarPunto(0.038f, -0.010f);
        return pico;
    }

    private Parte crearOjo() {
        Parte ojo = new Parte(new float[] { 0.08f, 0.08f, 0.12f });
        ojo.setVisibleConTextura(false);
        ojo.agregarPunto(0.008f, 0.015f)
                .agregarPunto(0.018f, 0.020f)
                .agregarPunto(0.022f, 0.012f)
                .agregarPunto(0.014f, 0.005f);
        return ojo;
    }

    private String obtenerFrameSprite() {
        float ciclo = (float) Math.sin(tiempoAnimacion * 18.0f);
        if (velocidadY > 0.15f || ciclo > 0.35f) {
            return resolverRutaPajaro(objetoEsAzul() ? Constantes.COLOR_JUGADOR2 : Constantes.COLOR_JUGADOR1, "upflap");
        }
        if (velocidadY < -0.25f || ciclo < -0.35f) {
            return resolverRutaPajaro(objetoEsAzul() ? Constantes.COLOR_JUGADOR2 : Constantes.COLOR_JUGADOR1,
                    "downflap");
        }
        return resolverRutaPajaro(objetoEsAzul() ? Constantes.COLOR_JUGADOR2 : Constantes.COLOR_JUGADOR1, "midflap");
    }

    private boolean objetoEsAzul() {
        return xInicial == Constantes.BIRD_X_PLAYER2;
    }

    private String resolverRutaPajaro(float[] colorBase, String frame) {
        String color = colorBase[2] > colorBase[0] ? "bluebird" : "yellowbird";
        return "/sprites/" + color + "-" + frame + ".png";
    }
}