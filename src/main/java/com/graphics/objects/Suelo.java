package com.graphics.objects;

import com.graphics.core.Objeto;
import com.graphics.core.Parte;
import com.graphics.render.Constantes;

public class Suelo extends Objeto {
    public Suelo() {
        Parte suelo = new Parte(Constantes.COLOR_SUELO);
        suelo.agregarPunto(-1.0f, 0.11f)
            .agregarPunto(1.0f, 0.11f)
            .agregarPunto(1.0f, -0.11f)
            .agregarPunto(-1.0f, -0.11f);
        suelo.setY(-0.90f);
        suelo.setVisibleConTextura(false);
        agregarParte(suelo);

        Parte cesped = new Parte(Constantes.COLOR_CESPED);
        cesped.agregarPunto(-1.0f, 0.05f)
            .agregarPunto(1.0f, 0.05f)
            .agregarPunto(1.0f, -0.05f)
            .agregarPunto(-1.0f, -0.05f);
        cesped.setY(Constantes.Y_CESPED);
        cesped.setVisibleConTextura(false);
        agregarParte(cesped);

        Parte base = crearSprite("/sprites/base.png", 2.25f, 0.28f, Constantes.COLOR_SUELO);
        base.setY(-0.86f);
        agregarParte(base);
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