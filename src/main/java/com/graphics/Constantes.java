package com.graphics;

public class Constantes {

    public static final int ANCHO_VENTANA = 900;
    public static final int ALTO_VENTANA = 700;

    // ==================== FÍSICA ====================
    public static final float GRAVEDAD = -1.9f;
    public static final float IMPULSO_SALTO = 0.85f;
    public static final float VELOCIDAD_MAX_CAIDA = -1.8f;

    // ==================== PÁJARO ====================
    public static final float Pajaro_ANCHO = 0.10f;
    public static final float Pajaro_ALTO = 0.10f;
    public static final float Pajaro_X_JUGADOR1 = -0.45f;
    public static final float Pajaro_X_JUGADOR2 = -0.30f;

    // ==================== TUBERÍA ====================
    public static final float Tuberia_ANCHO = 0.18f;
    public static final float Tuberia_GAP_ALTO = 0.48f;
    public static final float Tuberia_VELOCIDAD_BASE = 0.62f;
    public static final float Tuberia_TIEMPO_ENTRE_BASE = 1.5f;
    public static final float Tuberia_GAP_MIN_Y = -0.45f;
    public static final float Tuberia_GAP_MAX_Y = 0.45f;

    // ==================== DIFICULTAD ====================
    public static final float VELOCIDAD_MAX_TUBERIAS = 1.4f;
    public static final float TIEMPO_MIN_ENTRE_TUBERIAS = 0.8f;
    public static final int PUNTOS_POR_NIVEL = 5;
    public static final int NIVEL_MAX = 10;
    public static final float INCREMENTO_VELOCIDAD_POR_NIVEL = 0.08f;
    public static final float REDUCCION_TIEMPO_POR_NIVEL = 0.08f;

    // ==================== COLORES PÁJARO ====================
    public static final float[] COLOR_JUGADOR1 = {0.97f, 0.84f, 0.20f};
    public static final float[] COLOR_JUGADOR2 = {0.25f, 0.55f, 0.95f};
    public static final float[] COLOR_ALAA_JUGADOR1 = {0.98f, 0.82f, 0.26f};
    public static final float[] COLOR_ALA_JUGADOR2 = {0.50f, 0.70f, 1.00f};

    // ==================== COLORES TUBERÍA ====================
    public static final float[] COLOR_TUBERIA = {0.18f, 0.70f, 0.25f};

    // ==================== COLORES FONDO ====================
    public static final float[] CIELO_COLOR_ALTO = {0.52f, 0.80f, 0.92f};
    public static final float[] CIELO_COLOR_BAJO = {0.35f, 0.60f, 0.75f};
    public static final float[] COLOR_SUELO = {0.50f, 0.30f, 0.10f};
    public static final float[] COLOR_CESPED = {0.40f, 0.25f, 0.08f};
    public static final float[] COLOR_NUBE = {1.0f, 1.0f, 1.0f};

    // ==================== COLORES UI ====================
    public static final float[] COLOR_PANEL_TITULO = {0.05f, 0.11f, 0.20f};
    public static final float[] COLOR_PANEL_MEDIA = {0.08f, 0.15f, 0.24f};
    public static final float[] COLOR_PANEL_INFERIOR = {0.10f, 0.18f, 0.28f};
    public static final float[] COLOR_GAME_OVER = {0.24f, 0.06f, 0.07f};
    public static final float[] COLOR_TEXTO_BLANCO = {0.95f, 0.95f, 0.95f};
    public static final float[] COLOR_GANA_P1 = {0.2f, 0.95f, 0.35f};
    public static final float[] COLOR_GANA_P2 = {0.2f, 0.95f, 0.35f};

    // ==================== TEXTOS ====================
    public static final String TEXTO_TITULO = "FLAPPY BIRD";
    public static final String TEXTO_2_JUGADORES = "2 JUGADORES";
    public static final String TEXTO_P1 = "P1: ESPACIO";
    public static final String TEXTO_P2 = "P2: W / UP";
    public static final String TEXTO_ENTER = "ENTER PARA JUGAR";
    public static final String TEXTO_GAME_OVER = "GAME OVER";
    public static final String TEXTO_GANA_P1 = "GANA P1";
    public static final String TEXTO_GANA_P2 = "GANA P2";
    public static final String TEXTO_EMPATE = "EMPATE";
    public static final String TEXTO_REINICIAR = "ESPACIO PARA REINICIAR";

    // ==================== ANIMACIÓN ====================
    public static final float FRECUENCIA_ALETEO = 0.15f;
    public static final float VELOCIDAD_PULSO_UI = 4.0f;
    public static final float AMPLITUD_PULSO_UI = 0.04f;
    public static final float BOB_PAJARO = 0.003f;
    public static final float FRECUENCIA_BOB_PAJARO = 10.0f;
    public static final float FRECUENCIA_ALETEO_PAJARO = 16.0f;

    // ==================== POSICIONES NDC ====================
    public static final float Y_SUELO = -0.80f;
    public static final float Y_CESPED = -0.78f;
    public static final float Y_PANEL_INFERIOR = -0.24f;

    // ==================== OFFSETS SHADERS ====================
    public static final String SHADER_VERTEX_SOURCE =
        "#version 330 core\n" +
        "layout (location = 0) in vec3 aPos;\n" +
        "uniform vec2 uOffset;\n" +
        "uniform vec2 uScale;\n" +
        "void main() {\n" +
        "    vec2 finalPos = aPos.xy * uScale + uOffset;\n" +
        "    gl_Position = vec4(finalPos, aPos.z, 1.0);\n" +
        "}\n";

    public static final String SHADER_FRAGMENT_SOURCE =
        "#version 330 core\n" +
        "uniform vec3 uColor;\n" +
        "out vec4 fragColor;\n" +
        "void main() {\n" +
        "    fragColor = vec4(uColor, 1.0);\n" +
        "}\n";

    private Constantes() {}
}