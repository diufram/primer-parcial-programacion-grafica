package com.graphics;

public class Pajaro {
    private final ObjetoEscena objeto;
    private final ParteSprite sprite;
    private final Parte alaRespaldo;
    private final float xInicial;
    private final float gravedad;
    private final float impulsoSalto;
    private final float velocidadMaxCaida;
    private final float ancho;
    private final float alto;
    private float y;
    private float velocidadY;
    private boolean vivo;
    private float tiempoAnimacion;

    public Pajaro(float x, float y, float gravedad, float impulsoSalto, float velocidadMaxCaida,
                  float ancho, float alto, float[] color) {
        this.objeto = new ObjetoEscena();
        this.sprite = crearSprite(color, ancho, alto);
        this.alaRespaldo = crearAla(color);
        this.xInicial = x;
        this.y = y;
        this.gravedad = gravedad;
        this.impulsoSalto = impulsoSalto;
        this.velocidadMaxCaida = velocidadMaxCaida;
        this.ancho = ancho;
        this.alto = alto;
        this.vivo = true;
        objeto.agregarParte(crearCuerpo(color));
        objeto.agregarParte(alaRespaldo);
        objeto.agregarParte(crearPico());
        objeto.agregarParte(crearOjo());
        objeto.agregarParte(sprite);
        objeto.setX(x);
        objeto.setY(y);
    }

    public void saltar() {
        if (!vivo) {
            return;
        }
        velocidadY = impulsoSalto;
        alaRespaldo.setRotacion(-0.7f);
    }

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

        objeto.setX(xInicial);
        objeto.setY(y);
        objeto.setRotacion(Math.max(-0.45f, Math.min(0.35f, velocidadY * 0.35f)));
        float aleteo = (float) Math.sin(tiempoAnimacion * 12.0f) * 0.25f;
        alaRespaldo.setRotacion(alaRespaldo.getRotacion() * 0.85f + aleteo * 0.15f);
        sprite.setRutaTextura(obtenerFrameSprite());
    }

    public void reiniciar(float nuevoY) {
        this.y = nuevoY;
        this.velocidadY = 0.0f;
        this.vivo = true;
        this.tiempoAnimacion = 0.0f;
        objeto.setX(xInicial);
        objeto.setY(nuevoY);
        objeto.setRotacion(0.0f);
        alaRespaldo.setRotacion(0.0f);
        sprite.setRutaTextura(obtenerFrameSprite());
    }

    public ObjetoEscena getObjeto() {
        return objeto;
    }

    public boolean estaVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public float getX() {
        return xInicial;
    }

    public float getY() {
        return y;
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

    private ParteSprite crearSprite(float[] colorBase, float ancho, float alto) {
        ParteSprite parte = new ParteSprite(resolverRutaPajaro(colorBase, "midflap"), ancho * 1.45f, alto * 1.15f, colorBase);
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
        Parte pico = new Parte(new float[] {1.00f, 0.52f, 0.10f});
        pico.setVisibleConTextura(false);
        pico.agregarPunto(0.038f, 0.008f)
            .agregarPunto(0.065f, 0.000f)
            .agregarPunto(0.038f, -0.010f);
        return pico;
    }

    private Parte crearOjo() {
        Parte ojo = new Parte(new float[] {0.08f, 0.08f, 0.12f});
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
            return resolverRutaPajaro(objetoEsAzul() ? Constantes.COLOR_JUGADOR2 : Constantes.COLOR_JUGADOR1, "downflap");
        }
        return resolverRutaPajaro(objetoEsAzul() ? Constantes.COLOR_JUGADOR2 : Constantes.COLOR_JUGADOR1, "midflap");
    }

    private boolean objetoEsAzul() {
        return xInicial == Juego.BIRD_X_PLAYER2;
    }

    private String resolverRutaPajaro(float[] colorBase, String frame) {
        String color = colorBase[2] > colorBase[0] ? "bluebird" : "yellowbird";
        return "/sprites/" + color + "-" + frame + ".png";
    }
}
