package com.graphics.objects;

import com.graphics.core.Objeto;
import com.graphics.core.Parte;
import com.graphics.render.Constantes;

public class GameOver extends Objeto {

    public GameOver() {
        Parte parte = new Parte(Constantes.COLOR_CIELO);
        parte.setRutaTextura("/sprites/gameover.png");
        parte.setVisibleSinTextura(false);
        parte.setAlpha(0.0f);
        parte.agregarPunto(-0.50f, 0.20f)
            .agregarPunto(0.50f, 0.20f)
            .agregarPunto(0.50f, -0.20f)
            .agregarPunto(-0.50f, -0.20f);
        agregarParte(parte);
    }

    public void setAlpha(float alpha) {
        partes.get(0).setAlpha(alpha);
    }
}