package com.graphics;

public class Tuberia {
    private final ObjetoEscena objeto;
    private float x;
    private final float gapCentroY;
    private boolean puntuadaJugador1;
    private boolean puntuadaJugador2;
    private final float ancho;
    private final float gapAlto;

    public Tuberia(float x, float gapCentroY, float ancho, float gapAlto) {
        this.objeto = new ObjetoEscena();
        this.x = x;
        this.gapCentroY = gapCentroY;
        this.ancho = ancho;
        this.gapAlto = gapAlto;
        this.puntuadaJugador1 = false;
        this.puntuadaJugador2 = false;
        objeto.setX(x);
        objeto.setY(gapCentroY);
        construirPartes();
    }

    public void actualizar(float velX, float dt) {
        x -= velX * dt;
        objeto.setX(x);
    }

    public ObjetoEscena getObjeto() {
        return objeto;
    }

    public float getX() {
        return x;
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
        boolean yaPuntuada = jugador == 1 ? puntuadaJugador1 : puntuadaJugador2;
        if (x + (ancho * 0.5f) < birdX && !yaPuntuada) {
            if (jugador == 1) {
                puntuadaJugador1 = true;
            } else {
                puntuadaJugador2 = true;
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

        Parte respaldoSuperior = crearRectangulo(0.0f, centroSuperiorLocal, ancho, altoSuperior, Constantes.COLOR_TUBERIA);
        respaldoSuperior.setVisibleConTextura(false);
        Parte respaldoInferior = crearRectangulo(0.0f, centroInferiorLocal, ancho, altoInferior, Constantes.COLOR_TUBERIA);
        respaldoInferior.setVisibleConTextura(false);

        ParteSprite superior = new ParteSprite("/sprites/pipe-green.png", ancho * 1.55f, altoSuperior, Constantes.COLOR_TUBERIA);
        superior.setY(centroSuperiorLocal);
        superior.setRotacion((float) Math.PI);

        ParteSprite inferior = new ParteSprite("/sprites/pipe-green.png", ancho * 1.55f, altoInferior, Constantes.COLOR_TUBERIA);
        inferior.setY(centroInferiorLocal);

        objeto.agregarParte(respaldoSuperior);
        objeto.agregarParte(respaldoInferior);
        objeto.agregarParte(superior);
        objeto.agregarParte(inferior);
    }

    private Parte crearRectangulo(float centroX, float centroY, float ancho, float alto, float[] color) {
        Parte parte = new Parte(color);
        float medioAncho = ancho * 0.5f;
        float medioAlto = alto * 0.5f;
        parte.agregarPunto(centroX - medioAncho, centroY + medioAlto)
            .agregarPunto(centroX + medioAncho, centroY + medioAlto)
            .agregarPunto(centroX + medioAncho, centroY - medioAlto)
            .agregarPunto(centroX - medioAncho, centroY - medioAlto);
        return parte;
    }
}
