package com.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Renderizador {
    private final int programa;
    private final int programaTextura;
    private final int vao;
    private final int vbo;
    private final int vaoTextura;
    private final int vboTextura;
    private final int ubicacionColor;
    private final int ubicacionSampler;
    private final Map<String, Integer> texturas;
    private boolean texturasHabilitadas;

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

        ubicacionColor = GL20.glGetUniformLocation(programa, "uColor");

        int vertexShaderTextura = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vertexShaderTextura, Constantes.SHADER_VERTEX_TEXTURA_SOURCE);
        GL20.glCompileShader(vertexShaderTextura);
        verificarShader(vertexShaderTextura, "Vertex textura");

        int fragmentShaderTextura = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fragmentShaderTextura, Constantes.SHADER_FRAGMENT_TEXTURA_SOURCE);
        GL20.glCompileShader(fragmentShaderTextura);
        verificarShader(fragmentShaderTextura, "Fragment textura");

        programaTextura = GL20.glCreateProgram();
        GL20.glAttachShader(programaTextura, vertexShaderTextura);
        GL20.glAttachShader(programaTextura, fragmentShaderTextura);
        GL20.glLinkProgram(programaTextura);

        if (GL20.glGetProgrami(programaTextura, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            throw new RuntimeException(
                    "Error al enlazar programa textura: " + GL20.glGetProgramInfoLog(programaTextura));
        }

        ubicacionSampler = GL20.glGetUniformLocation(programaTextura, "uTexture");

        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);
        GL20.glDeleteShader(vertexShaderTextura);
        GL20.glDeleteShader(fragmentShaderTextura);

        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, 0L, GL15.GL_DYNAMIC_DRAW);
        GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 2 * Float.BYTES, 0);
        GL20.glEnableVertexAttribArray(0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);

        vaoTextura = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoTextura);

        vboTextura = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboTextura);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, 0L, GL15.GL_DYNAMIC_DRAW);
        GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 4 * Float.BYTES, 0);
        GL20.glEnableVertexAttribArray(0);
        GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 4 * Float.BYTES, 2L * Float.BYTES);
        GL20.glEnableVertexAttribArray(1);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);

        texturas = new HashMap<>();
        texturasHabilitadas = true;
    }

    public void prepararFrame() {
        GL11.glClearColor(Constantes.COLOR_CIELO[0], Constantes.COLOR_CIELO[1], Constantes.COLOR_CIELO[2], 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void dibujarObjeto(ObjetoEscena objeto) {
        for (Parte parte : objeto.getPartes()) {
            dibujarParte(parte, objeto.getX(), objeto.getY(), objeto.getRotacion(), objeto.getEscala(), null);
        }
    }

    public void dibujarRect(float centroX, float centroY, float ancho, float alto, float[] color) {
        List<Punto> puntos = new ArrayList<>();
        float medioAncho = ancho * 0.5f;
        float medioAlto = alto * 0.5f;
        puntos.add(new Punto(centroX - medioAncho, centroY + medioAlto));
        puntos.add(new Punto(centroX + medioAncho, centroY + medioAlto));
        puntos.add(new Punto(centroX + medioAncho, centroY - medioAlto));
        puntos.add(new Punto(centroX - medioAncho, centroY - medioAlto));
        dibujarPoligono(puntos, color);
    }

    public void limpiar() {
        GL30.glDeleteVertexArrays(vao);
        GL30.glDeleteVertexArrays(vaoTextura);
        GL15.glDeleteBuffers(vbo);
        GL15.glDeleteBuffers(vboTextura);
        GL20.glDeleteProgram(programa);
        GL20.glDeleteProgram(programaTextura);
        for (int textura : texturas.values()) {
            GL11.glDeleteTextures(textura);
        }
    }

    public boolean isTexturasHabilitadas() {
        return texturasHabilitadas;
    }

    public void alternarTexturas() {
        texturasHabilitadas = !texturasHabilitadas;
    }

    private void dibujarParte(Parte parte, float parentX, float parentY, float parentRotacion,
            float parentEscala, float[] colorHeredado) {
        if ((texturasHabilitadas && !parte.isVisibleConTextura())
                || (!texturasHabilitadas && !parte.isVisibleSinTextura())) {
            return;
        }

        float[] offset = transformar(parte.getX() * parentEscala, parte.getY() * parentEscala, parentRotacion);
        float origenX = parentX + offset[0];
        float origenY = parentY + offset[1];
        float rotacionMundo = parentRotacion + parte.getRotacion();
        float escalaMundo = parentEscala * parte.getEscala();
        float[] colorParte = parte.getColor() != null ? parte.getColor() : colorHeredado;
        float[] offsetPivote = transformar(parte.getPivoteX() * escalaMundo, parte.getPivoteY() * escalaMundo,
                rotacionMundo);
        float pivoteMundoX = origenX + offsetPivote[0];
        float pivoteMundoY = origenY + offsetPivote[1];

        boolean esSprite = parte.usaTextura();
        if (parte.getPuntos().size() >= 3 && (colorParte != null || esSprite)) {
            List<Punto> puntosMundo = new ArrayList<>();
            for (Punto punto : parte.getPuntos()) {
                float localX = (punto.getX() - parte.getPivoteX()) * escalaMundo;
                float localY = (punto.getY() - parte.getPivoteY()) * escalaMundo;
                float[] transformado = transformar(localX, localY, rotacionMundo);
                puntosMundo.add(new Punto(pivoteMundoX + transformado[0], pivoteMundoY + transformado[1]));
            }
            if (esSprite) {
                dibujarSprite(parte, puntosMundo);
            } else {
                dibujarPoligono(puntosMundo, colorParte);
            }
        }
    }

    private void dibujarPoligono(List<Punto> puntos, float[] color) {
        if (puntos.size() < 3) {
            return;
        }

        GL20.glUseProgram(programa);
        GL30.glBindVertexArray(vao);

        float[] vertices = new float[(puntos.size() - 2) * 6];
        Punto base = puntos.get(0);
        int indice = 0;
        for (int i = 1; i < puntos.size() - 1; i++) {
            Punto b = puntos.get(i);
            Punto c = puntos.get(i + 1);
            vertices[indice++] = base.getX();
            vertices[indice++] = base.getY();
            vertices[indice++] = b.getX();
            vertices[indice++] = b.getY();
            vertices[indice++] = c.getX();
            vertices[indice++] = c.getY();
        }

        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
        buffer.put(vertices).flip();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_DYNAMIC_DRAW);
        GL20.glUniform3f(ubicacionColor, color[0], color[1], color[2]);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertices.length / 2);
    }

    private void dibujarSprite(Parte parte, List<Punto> puntos) {
        if (puntos.size() != 4) {
            return;
        }

        if (!texturasHabilitadas) {
            dibujarPoligono(puntos, parte.getColor());
            return;
        }

        int textura = obtenerTextura(parte.getRutaTextura());
        GL20.glUseProgram(programaTextura);
        GL30.glBindVertexArray(vaoTextura);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textura);
        GL20.glUniform1i(ubicacionSampler, 0);

        float[] vertices = {
                puntos.get(0).getX(), puntos.get(0).getY(), 0.0f, 0.0f,
                puntos.get(1).getX(), puntos.get(1).getY(), 1.0f, 0.0f,
                puntos.get(2).getX(), puntos.get(2).getY(), 1.0f, 1.0f,
                puntos.get(0).getX(), puntos.get(0).getY(), 0.0f, 0.0f,
                puntos.get(2).getX(), puntos.get(2).getY(), 1.0f, 1.0f,
                puntos.get(3).getX(), puntos.get(3).getY(), 0.0f, 1.0f
        };

        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
        buffer.put(vertices).flip();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboTextura);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_DYNAMIC_DRAW);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
    }

    private int obtenerTextura(String rutaTextura) {
        Integer textura = texturas.get(rutaTextura);
        if (textura != null) {
            return textura;
        }

        try (InputStream input = Renderizador.class.getResourceAsStream(rutaTextura)) {
            if (input == null) {
                throw new RuntimeException("No se encontro la textura: " + rutaTextura);
            }

            BufferedImage imagen = ImageIO.read(input);
            int ancho = imagen.getWidth();
            int alto = imagen.getHeight();
            int[] pixeles = new int[ancho * alto];
            imagen.getRGB(0, 0, ancho, alto, pixeles, 0, ancho);

            java.nio.ByteBuffer buffer = BufferUtils.createByteBuffer(ancho * alto * 4);
            for (int y = 0; y < alto; y++) {
                for (int x = 0; x < ancho; x++) {
                    int pixel = pixeles[y * ancho + x];
                    buffer.put((byte) ((pixel >> 16) & 0xFF));
                    buffer.put((byte) ((pixel >> 8) & 0xFF));
                    buffer.put((byte) (pixel & 0xFF));
                    buffer.put((byte) ((pixel >> 24) & 0xFF));
                }
            }
            buffer.flip();

            int id = GL11.glGenTextures();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, ancho, alto, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE,
                    buffer);

            texturas.put(rutaTextura, id);
            return id;
        } catch (IOException e) {
            throw new RuntimeException("No se pudo cargar la textura: " + rutaTextura, e);
        }
    }

    private float[] transformar(float x, float y, float rotacion) {
        float cos = (float) Math.cos(rotacion);
        float sin = (float) Math.sin(rotacion);
        return new float[] {
                x * cos - y * sin,
                x * sin + y * cos
        };
    }

    private void verificarShader(int shader, String tipo) {
        if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            throw new RuntimeException(tipo + " shader: " + GL20.glGetShaderInfoLog(shader));
        }
    }
}
