# Flappy Bird (LWJGL + OpenGL)

Proyecto del parcial de Programación Gráfica en Java usando **LWJGL + GLFW + OpenGL 3.3**.

Incluye:
- Juego estilo Flappy Bird para 2 jugadores simultáneos.
- Pájaros compuestos por figuras geométricas.
- Dificultad progresiva por puntaje.
- Pantalla de inicio, HUD y pantalla de game over.

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

- Jugador 1: `ESPACIO` para saltar
- Jugador 2: `W` o `FLECHA ARRIBA` para saltar
- Reiniciar (game over): `ESPACIO` o `ENTER`
- Salir: `ESC`

## Estructura principal

- `src/main/java/com/graphics/AppFlappyBird.java`: ciclo principal, render y estados UI
- `src/main/java/com/graphics/Game.java`: lógica del juego, colisiones, puntaje y dificultad
- `src/main/java/com/graphics/Bird.java`: modelo/dibujo/animación del pájaro
- `src/main/java/com/graphics/Pipe.java`: obstáculos
- `src/main/java/com/graphics/InputManager.java`: manejo de teclas
- `src/main/java/com/graphics/TextRenderer.java`: texto bitmap para HUD/pantallas
- `src/main/java/com/graphics/SoundManager.java`: efectos de sonido (base)

## Problema común: "no encuentra POM"

Si Maven indica que no encuentra `pom.xml`, ejecuta los comandos dentro de la carpeta raíz del proyecto (donde está el `pom.xml`).
