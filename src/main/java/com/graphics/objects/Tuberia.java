package com.graphics.objects;

import com.graphics.core.Objeto;
import com.graphics.core.Parte;
import com.graphics.render.Constantes;

public class Tuberia extends Objeto {
    private final float gapCentroY;
    private final float gapAlto;
    private boolean puntuadaJugador1;
    private boolean puntuadaJugador2;
    private boolean puntuadaJugador3;
    private final float ancho;
    private float velocidad;

    public Tuberia(float x, float gapCentroY, float ancho, float gapAlto) {
        this.gapCentroY = gapCentroY;
        this.gapAlto = gapAlto;
        this.ancho = ancho;
        this.puntuadaJugador1 = false;
        this.puntuadaJugador2 = false;
        this.puntuadaJugador3 = false;
        this.velocidad = 0.62f;
        setX(x);
        setY(gapCentroY);
        construirPartes();
    }

    public void setVelocidad(float velocidad) {
        this.velocidad = velocidad;
    }

    @Override
    public void actualizar(float dt) {
        x -= velocidad * dt;
        setX(x);
    }

    public float getAncho() {
        return ancho;
    }

    public float getGapSuperior() {
        return gapCentroY + (gapAlto * 0.5f);
    }

    public float getGapInferior() {
        return gapCentroY - (gapAlto * 0.5f);
    }

    public boolean estaFueraDePantalla() {
        return x + (ancho * 0.5f) < -1.3f;
    }

    public boolean pasoPorX(float birdX, int jugador) {
        boolean yaPuntuada = jugador == 1 ? puntuadaJugador1 : (jugador == 2 ? puntuadaJugador2 : puntuadaJugador3);
        if (x + (ancho * 0.5f) < birdX && !yaPuntuada) {
            if (jugador == 1) {
                puntuadaJugador1 = true;
            } else if (jugador == 2) {
                puntuadaJugador2 = true;
            } else {
                puntuadaJugador3 = true;
            }
            return true;
        }
        return false;
    }

    private void construirPartes() {
        float altoSuperior = Constantes.LIMITE_ARRIBA - getGapSuperior();
        float altoInferior = getGapInferior() - Constantes.LIMITE_ABAJO;
        float centroSuperiorLocal = (gapAlto * 0.5f) + (altoSuperior * 0.5f);
        float centroInferiorLocal = -(gapAlto * 0.5f) - (altoInferior * 0.5f);

        Parte respaldoSuperior = new Parte(Constantes.COLOR_TUBERIA);
        float medioAncho = ancho * 0.5f;
        float medioAltoSuperior = altoSuperior * 0.5f;
        respaldoSuperior.agregarPunto(-medioAncho, centroSuperiorLocal + medioAltoSuperior)
            .agregarPunto(medioAncho, centroSuperiorLocal + medioAltoSuperior)
            .agregarPunto(medioAncho, centroSuperiorLocal - medioAltoSuperior)
            .agregarPunto(-medioAncho, centroSuperiorLocal - medioAltoSuperior);
        respaldoSuperior.setVisibleConTextura(false);
        agregarParte(respaldoSuperior);

        Parte respaldoInferior = new Parte(Constantes.COLOR_TUBERIA);
        float medioAltoInferior = altoInferior * 0.5f;
        respaldoInferior.agregarPunto(-medioAncho, centroInferiorLocal + medioAltoInferior)
            .agregarPunto(medioAncho, centroInferiorLocal + medioAltoInferior)
            .agregarPunto(medioAncho, centroInferiorLocal - medioAltoInferior)
            .agregarPunto(-medioAncho, centroInferiorLocal - medioAltoInferior);
        respaldoInferior.setVisibleConTextura(false);
        agregarParte(respaldoInferior);

        Parte superior = crearParteTextura("/sprites/pipe-green.png", ancho * 1.55f, altoSuperior, Constantes.COLOR_TUBERIA);
        superior.setY(centroSuperiorLocal);
        superior.setRotacion((float) Math.PI);
        agregarParte(superior);

        Parte inferior = crearParteTextura("/sprites/pipe-green.png", ancho * 1.55f, altoInferior, Constantes.COLOR_TUBERIA);
        inferior.setY(centroInferiorLocal);
        agregarParte(inferior);
    }

    private Parte crearParteTextura(String rutaTextura, float ancho, float alto, float[] colorRespaldo) {
        Parte parte = new Parte(colorRespaldo);
        parte.setRutaTextura(rutaTextura);
        parte.setVisibleSinTextura(false);
        float hw = ancho * 0.5f;
        float hh = alto * 0.5f;
        parte.agregarPunto(-hw, hh)
            .agregarPunto(hw, hh)
            .agregarPunto(hw, -hh)
            .agregarPunto(-hw, -hh);
        return parte;
    }
}
