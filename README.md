# Escenario Poligonal (LWJGL + OpenGL)

Proyecto simplificado de Programación Gráfica en Java usando **LWJGL + GLFW + OpenGL 3.3**.

La base del proyecto ahora es jerárquica:
- `Escenario`
- `ObjetoEscena`
- `Parte`
- `Punto`

Cada `Parte` define un polígono mediante una lista de puntos relativos. OpenGL renderiza esos polígonos como triángulos.

## Requisitos

- Java 17 o superior
- Maven 3.9+
- Dependencias LWJGL con natives del sistema (en este repo ya están configuradas)

## Compilar

```bash
mvn compile
```

## Ejecutar

```bash
mvn exec:exec -DmainClass=com.graphics.AppFlappyBird
```

## Controles

- Jugador 1: `ESPACIO`
- Jugador 2: `W` o `FLECHA ARRIBA`
- Reiniciar: `ESPACIO`, `ENTER` o `R`
- Salir: `ESC`

## Estructura principal

- `src/main/java/com/graphics/AppFlappyBird.java`: ventana, loop principal y entrada
- `src/main/java/com/graphics/Renderizador.java`: shader y dibujo de polígonos
- `src/main/java/com/graphics/Escenario.java`: contenedor de objetos
- `src/main/java/com/graphics/ObjetoEscena.java`: transformación global y partes
- `src/main/java/com/graphics/Parte.java`: polígono relativo y subpartes
- `src/main/java/com/graphics/Punto.java`: punto base de la geometría
- `src/main/java/com/graphics/Juego.java`: lógica del juego
- `src/main/java/com/graphics/Pajaro.java`: entidad del jugador construida con partes
- `src/main/java/com/graphics/Tuberia.java`: obstáculo poligonal
- `src/main/java/com/graphics/GeneradorFondo.java`: fondo del escenario

## Problema común: "no encuentra POM"

Si Maven indica que no encuentra `pom.xml`, ejecuta los comandos dentro de la carpeta raíz del proyecto (donde está el `pom.xml`).
