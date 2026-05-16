package com.graphics.objects;

import com.graphics.core.Objeto;
import com.graphics.core.Parte;
import com.graphics.render.Constantes;

public class Cielo extends Objeto {
    public Cielo() {
        Parte cieloPoligonal = new Parte(Constantes.COLOR_CIELO);
        cieloPoligonal.agregarPunto(-1.0f, 1.0f)
            .agregarPunto(1.0f, 1.0f)
            .agregarPunto(1.0f, -1.0f)
            .agregarPunto(-1.0f, -1.0f);
        cieloPoligonal.setVisibleConTextura(false);
        agregarParte(cieloPoligonal);

        Parte cieloSprite = crearSprite("/sprites/background-day.png", 2.0f, 2.0f, Constantes.COLOR_CIELO);
        agregarParte(cieloSprite);
    }

    private Parte crearSprite(String ruta, float ancho, float alto, float[] color) {
        Parte parte = new Parte(color);
        parte.setRutaTextura(ruta);
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