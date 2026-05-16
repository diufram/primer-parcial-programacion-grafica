package com.graphics;

public class GeneradorFondo {
    public GeneradorFondo(Escenario escenario) {
        escenario.agregarObjeto(crearCielo());
        escenario.agregarObjeto(crearSuelo());
        escenario.agregarObjeto(crearNube(-0.75f, 0.72f, 0.10f));
        escenario.agregarObjeto(crearNube(0.10f, 0.82f, 0.14f));
        escenario.agregarObjeto(crearNube(0.76f, 0.62f, 0.15f));
        escenario.agregarObjeto(crearNube(-0.18f, 0.54f, 0.12f));
    }

    private ObjetoEscena crearCielo() {
        ObjetoEscena objeto = new ObjetoEscena();
        Parte cieloPoligonal = rectangulo(0.0f, 0.0f, 2.0f, 2.0f, Constantes.COLOR_CIELO);
        cieloPoligonal.setVisibleConTextura(false);
        ParteSprite cieloSprite = new ParteSprite("/sprites/background-day.png", 2.0f, 2.0f, Constantes.COLOR_CIELO);
        objeto.agregarParte(cieloPoligonal);
        objeto.agregarParte(cieloSprite);
        return objeto;
    }

    private ObjetoEscena crearSuelo() {
        ObjetoEscena objeto = new ObjetoEscena();
        Parte suelo = rectangulo(0.0f, 0.0f, 2.0f, 0.22f, Constantes.COLOR_SUELO);
        suelo.setY(-0.90f);
        suelo.setVisibleConTextura(false);
        Parte cesped = rectangulo(0.0f, 0.0f, 2.0f, 0.10f, Constantes.COLOR_CESPED);
        cesped.setY(Constantes.Y_CESPED);
        cesped.setVisibleConTextura(false);
        ParteSprite base = new ParteSprite("/sprites/base.png", 2.25f, 0.28f, Constantes.COLOR_SUELO);
        base.setY(-0.86f);
        objeto.agregarParte(suelo);
        objeto.agregarParte(cesped);
        objeto.agregarParte(base);
        return objeto;
    }

    private ObjetoEscena crearNube(float x, float y, float escala) {
        ObjetoEscena nube = new ObjetoEscena();
        nube.setX(x);
        nube.setY(y);
        nube.setEscala(escala);
        Parte base = poligonoNube(0.55f, 0.22f);
        Parte izquierda = poligonoNube(0.34f, 0.20f);
        Parte derecha = poligonoNube(0.30f, 0.18f);
        izquierda.setX(-0.34f);
        izquierda.setY(0.08f);
        derecha.setX(0.34f);
        derecha.setY(0.06f);
        nube.agregarParte(base);
        nube.agregarParte(izquierda);
        nube.agregarParte(derecha);
        return nube;
    }

    private Parte poligonoNube(float rx, float ry) {
        Parte parte = new Parte(Constantes.COLOR_NUBE);
        parte.agregarPunto(-rx, 0.0f)
                .agregarPunto(-rx * 0.70f, ry * 0.70f)
                .agregarPunto(0.0f, ry)
                .agregarPunto(rx * 0.70f, ry * 0.70f)
                .agregarPunto(rx, 0.0f)
                .agregarPunto(rx * 0.60f, -ry * 0.70f)
                .agregarPunto(0.0f, -ry)
                .agregarPunto(-rx * 0.60f, -ry * 0.70f);
        return parte;
    }

    private Parte rectangulo(float centroX, float centroY, float ancho, float alto, float[] color) {
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
