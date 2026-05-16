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
mvn exec:exec -DmainClass=com.graphics.game.AppFlappyBird
```

## Controles

- Jugador 1: `ESPACIO`
- Jugador 2: `W` o `FLECHA ARRIBA`
- Reiniciar: `ESPACIO`, `ENTER` o `R`
- Salir: `ESC`
