package com.graphics.objects;

import com.graphics.core.Objeto;
import com.graphics.core.Parte;

public class FondoOscuro extends Objeto {

    public FondoOscuro() {
        Parte parte = new Parte(new float[] { 0.0f, 0.0f, 0.0f });
        parte.setAlpha(0.0f);
        parte.setVisibleSinTextura(false);
        parte.agregarPunto(-1.0f, 1.0f)
                .agregarPunto(1.0f, 1.0f)
                .agregarPunto(1.0f, -1.0f)
                .agregarPunto(-1.0f, -1.0f);
        agregarParte(parte);
    }

    public void setAlpha(float alpha) {
        partes.get(0).setAlpha(alpha);
    }
}