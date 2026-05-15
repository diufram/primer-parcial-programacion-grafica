package com.graphics;

import com.graphics.formas.Pixel;
import com.graphics.formas.Sprite;
import com.graphics.formas.SpriteFont;
import java.nio.FloatBuffer;
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

    private final SpriteFont fuente;

    private static final float PIXEL_TEXTO = 0.010f;
    private static final float GLIFO_ANCHO = 5f;
    private static final float ESPACIADO = 2f;

    public Renderizador() {
        fuente = new SpriteFont();
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
            Sprite glifo = fuente.getGlifo(c);
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

    private void dibujarGlifo(Sprite glifo, float x, float y, float escala, float r, float g, float b) {
        float pixel = PIXEL_TEXTO * escala;
        for (Pixel p : glifo.getPixeles()) {
            float px = x + p.getX() * pixel + pixel * 0.5f;
            float py = y - p.getY() * pixel - pixel * 0.5f;
            dibujarRect(px, py, pixel, pixel, r, g, b);
        }
    }

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

}
