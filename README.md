# Flappy Bird 2P - Multijugador Local

Juego de programación gráfica usando **LWJGL + GLFW + OpenGL 3.3**.

---

## Arquitectura del Proyecto

### Jerarquía de Clases

```
Punto → Parte → Objeto → Escenario
```

| Clase | Descripción |
|-------|-------------|
| `Punto` | Vértice con coordenadas (x, y) |
| `Parte` | Polígono definido por lista de puntos relativos. Incluye color, textura, pivote, rotación, escala y alpha |
| `Objeto` | Entidad abstracta con posición (x, y), rotación, escala y lista de partes. Método `actualizar(dt)` para lógica |
| `Escenario` | Contenedor que gestiona colección de objetos y los renderiza |

### Sistema de Renderizado

- **OpenGL 3.3** con shaders personalizados
- **`Renderizador`** maneja dos programas:
  - **Sin textura**: renderiza polígonos de color sólido
  - **Con textura**: renderiza sprites con alpha
- Los polígonos se triangulan automáticamente (fan triangulation)
- Transformaciones: traslación, rotación, escala con pivote

### Texturas

- Sistema de **toggle** con tecla `TAB`
- Cada `Parte` puede tener:
  - `visibleConTextura`: se muestra con texturas
  - `visibleSinTextura`: se muestra sin texturas (solo color)
- Texturas cargadas desde recursos (`.png`)

### Audio

- **`GestorAudio`** usa Java Sound API
- Sounds: salto, punto, golpe, muerte, transición

---

## Estructura del Proyecto

```
src/main/java/com/graphics/
├── core/
│   ├── Punto.java          # Vértice 2D
│   ├── Parte.java          # Polígono con puntos relativos
│   ├── Objeto.java         # Entidad abstracta
│   └── Escenario.java      # Contenedor de objetos
├── objects/
│   ├── Pajaro.java         # Jugador (física, animación)
│   ├── Tuberia.java        # Obstáculo
│   ├── Suelo.java          # Suelo
│   ├── Cielo.java          # Fondo
│   ├── Nube.java           # Decoración
│   ├── GameOver.java       # Pantalla fin
│   └── FondoOscuro.java   # Overlay
├── render/
│   ├── Renderizador.java   # OpenGL rendering
│   └── Constantes.java     # Colores, shaders, tamaños
├── audio/
│   └── GestorAudio.java    # Gestión de sonidos
└── game/
    ├── AppFlappyBird.java  # Entry point (GLFW setup)
    └── Juego.java          # Lógica del juego

src/main/resources/
├── sprites/                # Texturas PNG
└── audio/                  # Archivos WAV
```

---

## Recursos

### Texturas (`src/main/resources/sprites/`)

| Archivo | Descripción |
|---------|-------------|
| `background-day.png`, `background-night.png` | Fondos |
| `yellowbird-*.png`, `bluebird-*.png`, `redbird-*.png` | Sprites de pájaros (3 estados) |
| `pipe-green.png`, `pipe-red.png` | Tuberías |
| `base.png` | Suelo |
| `0.png` - `9.png` | Dígitos para puntuación |
| `message.png` | Pantalla inicial |
| `gameover.png` | Pantalla game over |

### Audio (`src/main/resources/audio/`)

| Archivo | Uso |
|---------|-----|
| `wing.wav` | Al saltar |
| `point.wav` | Al pasar tubería |
| `hit.wav` | Al colisionar |
| `die.wav` | Al morir |
| `swoosh.wav` | Transiciones |

---

## Controles

| Acción | Tecla |
|--------|-------|
| Jugador 1 (amarillo) | `ESPACIO` |
| Jugador 2 (azul) | `W` o `FLECHA ARRIBA` |
| Jugador 3 (rojo) | `I` |
| Alternar texturas | `TAB` |
| Reiniciar | `ESPACIO`, `ENTER` o `R` |
| Salir | `ESC` |

---

## Compilar y Ejecutar

```bash
mvn compile
mvn exec:exec -DmainClass=com.graphics.game.AppFlappyBird
```

---

## Detalles Técnicos

- **Java**: 17+
- **Maven**: 3.9+
- **OpenGL**: 3.3
- **Victoria**: Primer jugador a **2 puntos** gana
- **Dificultad**: Las tuberías aceleran a medida que suben los scores