package com.graphics;

public class Constantes {

    public static final int ANCHO_VENTANA = 900;
    public static final int ALTO_VENTANA = 700;

    // ==================== COLORES PÁJARO ====================
    public static final float[] COLOR_JUGADOR1 = {0.97f, 0.84f, 0.20f};
    public static final float[] COLOR_JUGADOR2 = {0.25f, 0.55f, 0.95f};

    // ==================== COLORES UI ====================
    public static final float[] COLOR_PANEL_TITULO = {0.05f, 0.11f, 0.20f};
    public static final float[] COLOR_PANEL_MEDIA = {0.08f, 0.15f, 0.24f};
    public static final float[] COLOR_PANEL_INFERIOR = {0.10f, 0.18f, 0.28f};
    public static final float[] COLOR_GAME_OVER = {0.24f, 0.06f, 0.07f};
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
    public static final float VELOCIDAD_PULSO_UI = 4.0f;
    public static final float AMPLITUD_PULSO_UI = 0.04f;

    // ==================== POSICIONES NDC ====================
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
