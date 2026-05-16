package com.graphics.render;

public class Constantes {
    public static final int ANCHO_VENTANA = 900;
    public static final int ALTO_VENTANA = 700;

    public static final float LIMITE_ARRIBA = 1.0f;
    public static final float LIMITE_ABAJO = -1.0f;
    public static final float Y_CESPED = -0.78f;

    public static final float[] COLOR_JUGADOR1 = {0.97f, 0.84f, 0.20f};
    public static final float[] COLOR_JUGADOR2 = {0.25f, 0.55f, 0.95f};
    public static final float[] COLOR_JUGADOR3 = {0.95f, 0.32f, 0.30f};
    public static final float[] COLOR_CIELO = {0.56f, 0.84f, 0.98f};
    public static final float[] COLOR_SUELO = {0.71f, 0.50f, 0.23f};
    public static final float[] COLOR_CESPED = {0.34f, 0.76f, 0.22f};
    public static final float[] COLOR_NUBE = {0.96f, 0.99f, 1.00f};
    public static final float[] COLOR_TUBERIA = {0.22f, 0.66f, 0.25f};
    public static final float[] COLOR_TUBERIA_BORDE = {0.12f, 0.42f, 0.15f};

    public static final float GRAVEDAD = -1.9f;
    public static final float IMPULSO_SALTO = 0.85f;
    public static final float VELOCIDAD_MAX_CAIDA = -1.8f;
    public static final float BIRD_ANCHO = 0.10f;
    public static final float BIRD_ALTO = 0.10f;
    public static final float BIRD_X_PLAYER1 = -0.45f;
    public static final float BIRD_X_PLAYER2 = -0.30f;
    public static final float BIRD_X_PLAYER3 = -0.15f;
    public static final float GAP_MIN_CENTRO = -0.45f;
    public static final float GAP_MAX_CENTRO = 0.45f;
    public static final float PIPE_ANCHO = 0.18f;
    public static final float GAP_ALTO = 0.48f;
    public static final float VELOCIDAD_TUBERIAS_INICIAL = 0.62f;
    public static final float VELOCIDAD_TUBERIAS_MAX = 1.4f;
    public static final float TIEMPO_ENTRE_TUBERIAS_INICIAL = 1.5f;
    public static final float TIEMPO_ENTRE_TUBERIAS_MIN = 0.8f;
    public static final float ALPHA_FONDO_OSCURO = 0.7f;

    public static final String SHADER_VERTEX_SOURCE =
        "#version 330 core\n" +
        "layout (location = 0) in vec2 aPos;\n" +
        "void main() {\n" +
        "    gl_Position = vec4(aPos, 0.0, 1.0);\n" +
        "}\n";

    public static final String SHADER_FRAGMENT_SOURCE =
        "#version 330 core\n" +
        "uniform vec4 uColor;\n" +
        "out vec4 fragColor;\n" +
        "void main() {\n" +
        "    fragColor = uColor;\n" +
        "}\n";

    public static final String SHADER_VERTEX_TEXTURA_SOURCE =
        "#version 330 core\n" +
        "layout (location = 0) in vec2 aPos;\n" +
        "layout (location = 1) in vec2 aTexCoord;\n" +
        "out vec2 vTexCoord;\n" +
        "void main() {\n" +
        "    gl_Position = vec4(aPos, 0.0, 1.0);\n" +
        "    vTexCoord = aTexCoord;\n" +
        "}\n";

    public static final String SHADER_FRAGMENT_TEXTURA_SOURCE =
        "#version 330 core\n" +
        "in vec2 vTexCoord;\n" +
        "uniform sampler2D uTexture;\n" +
        "uniform float uAlpha;\n" +
        "out vec4 fragColor;\n" +
        "void main() {\n" +
        "    fragColor = texture(uTexture, vTexCoord) * uAlpha;\n" +
        "}\n";

    private Constantes() {
    }
}
