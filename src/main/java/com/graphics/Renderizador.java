package com.graphics;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Renderizador {

    private final int programa;
    private final int vao;
    private final int vbo;
    private final int ubicacionOffset;
    private final int ubicacionEscala;
    private final int ubicacionColor;

    private static final Map<Character, String[]> FUENTE = new HashMap<>();

    static {
        FUENTE.put('A', glifo("01110", "10001", "10001", "11111", "10001", "10001", "10001"));
        FUENTE.put('B', glifo("11110", "10001", "10001", "11110", "10001", "10001", "11110"));
        FUENTE.put('C', glifo("01110", "10001", "10000", "10000", "10000", "10001", "01110"));
        FUENTE.put('D', glifo("11100", "10010", "10001", "10001", "10001", "10010", "11100"));
        FUENTE.put('E', glifo("11111", "10000", "10000", "11110", "10000", "10000", "11111"));
        FUENTE.put('F', glifo("11111", "10000", "10000", "11110", "10000", "10000", "10000"));
        FUENTE.put('G', glifo("01110", "10001", "10000", "10111", "10001", "10001", "01110"));
        FUENTE.put('H', glifo("10001", "10001", "10001", "11111", "10001", "10001", "10001"));
        FUENTE.put('I', glifo("11111", "00100", "00100", "00100", "00100", "00100", "11111"));
        FUENTE.put('J', glifo("00111", "00010", "00010", "00010", "10010", "10010", "01100"));
        FUENTE.put('K', glifo("10001", "10010", "10100", "11000", "10100", "10010", "10001"));
        FUENTE.put('L', glifo("10000", "10000", "10000", "10000", "10000", "10000", "11111"));
        FUENTE.put('M', glifo("10001", "11011", "10101", "10001", "10001", "10001", "10001"));
        FUENTE.put('N', glifo("10001", "11001", "10101", "10011", "10001", "10001", "10001"));
        FUENTE.put('O', glifo("01110", "10001", "10001", "10001", "10001", "10001", "01110"));
        FUENTE.put('P', glifo("11110", "10001", "10001", "11110", "10000", "10000", "10000"));
        FUENTE.put('Q', glifo("01110", "10001", "10001", "10001", "10101", "10010", "01101"));
        FUENTE.put('R', glifo("11110", "10001", "10001", "11110", "10100", "10010", "10001"));
        FUENTE.put('S', glifo("01111", "10000", "10000", "01110", "00001", "00001", "11110"));
        FUENTE.put('T', glifo("11111", "00100", "00100", "00100", "00100", "00100", "00100"));
        FUENTE.put('U', glifo("10001", "10001", "10001", "10001", "10001", "10001", "01110"));
        FUENTE.put('V', glifo("10001", "10001", "10001", "10001", "10001", "01010", "00100"));
        FUENTE.put('W', glifo("10001", "10001", "10001", "10101", "10101", "10101", "01010"));
        FUENTE.put('X', glifo("10001", "10001", "01010", "00100", "01010", "10001", "10001"));
        FUENTE.put('Y', glifo("10001", "10001", "01010", "00100", "00100", "00100", "00100"));
        FUENTE.put('Z', glifo("11111", "00001", "00010", "00100", "01000", "10000", "11111"));
        FUENTE.put('0', glifo("01110", "10001", "10011", "10101", "11001", "10001", "01110"));
        FUENTE.put('1', glifo("00100", "01100", "00100", "00100", "00100", "00100", "01110"));
        FUENTE.put('2', glifo("01110", "10001", "00001", "00010", "00100", "01000", "11111"));
        FUENTE.put('3', glifo("11110", "00001", "00001", "01110", "00001", "00001", "11110"));
        FUENTE.put('4', glifo("00010", "00110", "01010", "10010", "11111", "00010", "00010"));
        FUENTE.put('5', glifo("11111", "10000", "10000", "11110", "00001", "00001", "11110"));
        FUENTE.put('6', glifo("01110", "10000", "10000", "11110", "10001", "10001", "01110"));
        FUENTE.put('7', glifo("11111", "00001", "00010", "00100", "01000", "01000", "01000"));
        FUENTE.put('8', glifo("01110", "10001", "10001", "01110", "10001", "10001", "01110"));
        FUENTE.put('9', glifo("01110", "10001", "10001", "01111", "00001", "00001", "01110"));
        FUENTE.put(':', glifo("00000", "00100", "00100", "00000", "00100", "00100", "00000"));
        FUENTE.put('/', glifo("00001", "00010", "00100", "00100", "01000", "10000", "00000"));
        FUENTE.put('!', glifo("00100", "00100", "00100", "00100", "00100", "00000", "00100"));
        FUENTE.put('-', glifo("00000", "00000", "00000", "11111", "00000", "00000", "00000"));
        FUENTE.put(' ', glifo("00000", "00000", "00000", "00000", "00000", "00000", "00000"));
    }

    private static final float PIXEL_TEXTO = 0.010f;
    private static final float GLIFO_ANCHO = 5f;
    private static final float ESPACIADO = 2f;

    public Renderizador() {
        int vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vertexShader, Constantes.SHADER_VERTEX_SOURCE);
        GL20.glCompileShader(vertexShader);
        verificarShader(vertexShader, "Vertex");

        int fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fragmentShader, Constantes.SHADER_FRAGMENT_SOURCE);
        GL20.glCompileShader(fragmentShader);
        verificarShader(fragmentShader, "Fragment");

        programa = GL20.glCreateProgram();
        GL20.glAttachShader(programa, vertexShader);
        GL20.glAttachShader(programa, fragmentShader);
        GL20.glLinkProgram(programa);

        if (GL20.glGetProgrami(programa, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            throw new RuntimeException("Error al enlazar programa: " + GL20.glGetProgramInfoLog(programa));
        }

        ubicacionOffset = GL20.glGetUniformLocation(programa, "uOffset");
        ubicacionEscala = GL20.glGetUniformLocation(programa, "uScale");
        ubicacionColor = GL20.glGetUniformLocation(programa, "uColor");

        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);

        float[] vertices = {
            -0.5f, -0.5f, 0.0f,
             0.5f, -0.5f, 0.0f,
             0.5f,  0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
             0.5f,  0.5f, 0.0f,
            -0.5f,  0.5f, 0.0f
        };

        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);

        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
        buffer.put(vertices).flip();
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 3 * Float.BYTES, 0);
        GL20.glEnableVertexAttribArray(0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }

    public void usarPrograma() {
        GL20.glUseProgram(programa);
        GL30.glBindVertexArray(vao);
    }

    public void dibujarRect(float x, float y, float ancho, float alto, float r, float g, float b) {
        GL20.glUniform2f(ubicacionOffset, x, y);
        GL20.glUniform2f(ubicacionEscala, ancho, alto);
        GL20.glUniform3f(ubicacionColor, r, g, b);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
    }

    public void dibujarTexto(String texto, float x, float y, float r, float g, float b) {
        dibujarTextoEscalado(texto, x, y, 1.0f, r, g, b);
    }

    public void dibujarTextoEscalado(String texto, float x, float y, float escala, float r, float g, float b) {
        float cursorX = x;
        float avance = (GLIFO_ANCHO + ESPACIADO) * PIXEL_TEXTO * escala;
        for (int i = 0; i < texto.length(); i++) {
            char c = Character.toUpperCase(texto.charAt(i));
            String[] glifo = FUENTE.getOrDefault(c, FUENTE.get(' '));
            dibujarGlifo(glifo, cursorX, y, escala, r, g, b);
            cursorX += avance;
        }
    }

    public void dibujarTextoCentrado(String texto, float centroX, float y, float r, float g, float b) {
        float ancho = texto.length() * (GLIFO_ANCHO + ESPACIADO) * PIXEL_TEXTO;
        dibujarTexto(texto, centroX - ancho * 0.5f, y, r, g, b);
    }

    public void dibujarTextoConSombra(String texto, float x, float y, float escala, float r, float g, float b) {
        dibujarTextoEscalado(texto, x + 0.006f, y - 0.006f, escala, 0.0f, 0.0f, 0.0f);
        dibujarTextoEscalado(texto, x, y, escala, r, g, b);
    }

    public void dibujarTextoConSombraCentrado(String texto, float centroX, float y, float escala, float r, float g, float b) {
        float ancho = texto.length() * (GLIFO_ANCHO + ESPACIADO) * PIXEL_TEXTO * escala;
        dibujarTextoConSombra(texto, centroX - ancho * 0.5f, y, escala, r, g, b);
    }

    private void dibujarGlifo(String[] glifo, float x, float y, float escala, float r, float g, float b) {
        float pixel = PIXEL_TEXTO * escala;
        for (int fila = 0; fila < glifo.length; fila++) {
            String linea = glifo[fila];
            for (int col = 0; col < linea.length(); col++) {
                if (linea.charAt(col) == '1') {
                    float px = x + col * pixel + pixel * 0.5f;
                    float py = y - fila * pixel - pixel * 0.5f;
                    dibujarRect(px, py, pixel, pixel, r, g, b);
                }
            }
        }
    }

    public int getUbicacionOffset() { return ubicacionOffset; }
    public int getUbicacionEscala() { return ubicacionEscala; }
    public int getUbicacionColor() { return ubicacionColor; }

    public void limpiar() {
        GL30.glDeleteVertexArrays(vao);
        GL15.glDeleteBuffers(vbo);
        GL20.glDeleteProgram(programa);
    }

    private void verificarShader(int shader, String tipo) {
        if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            throw new RuntimeException(tipo + " shader: " + GL20.glGetShaderInfoLog(shader));
        }
    }

    private static String[] glifo(String... filas) {
        return filas;
    }
}