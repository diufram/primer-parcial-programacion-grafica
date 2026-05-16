package com.graphics.objects;

import com.graphics.core.Objeto;
import com.graphics.core.Parte;
import com.graphics.render.Constantes;

public class Nube extends Objeto {
    public Nube(float x, float y, float escala) {
        setX(x);
        setY(y);
        setEscala(escala);

        Parte base = poligonoNube(0.55f, 0.22f);
        Parte izquierda = poligonoNube(0.34f, 0.20f);
        Parte derecha = poligonoNube(0.30f, 0.18f);
        izquierda.setX(-0.34f);
        izquierda.setY(0.08f);
        derecha.setX(0.34f);
        derecha.setY(0.06f);

        agregarParte(base);
        agregarParte(izquierda);
        agregarParte(derecha);
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
}