package com.graphics;

public class ParteSprite extends Parte {
    private String rutaTextura;

    public ParteSprite(String rutaTextura, float ancho, float alto) {
        this(rutaTextura, ancho, alto, new float[] {1.0f, 1.0f, 1.0f});
    }

    public ParteSprite(String rutaTextura, float ancho, float alto, float[] colorRespaldo) {
        super(colorRespaldo);
        this.rutaTextura = rutaTextura;
        setVisibleSinTextura(false);
        definirRectangulo(ancho, alto);
    }

    public String getRutaTextura() {
        return rutaTextura;
    }

    public void setRutaTextura(String rutaTextura) {
        this.rutaTextura = rutaTextura;
    }

    private void definirRectangulo(float ancho, float alto) {
        float medioAncho = ancho * 0.5f;
        float medioAlto = alto * 0.5f;
        agregarPunto(-medioAncho, medioAlto)
            .agregarPunto(medioAncho, medioAlto)
            .agregarPunto(medioAncho, -medioAlto)
            .agregarPunto(-medioAncho, -medioAlto);
    }
}
