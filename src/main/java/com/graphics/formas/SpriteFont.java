package com.graphics.formas;

import java.util.HashMap;
import java.util.Map;

public class SpriteFont {
    private final Map<Character, Sprite> glifos;

    public SpriteFont() {
        this.glifos = new HashMap<>();
        cargarGlifos();
    }

    public Sprite getGlifo(char c) {
        return glifos.getOrDefault(Character.toUpperCase(c), glifos.get(' '));
    }

    private void cargarGlifos() {
        glifos.put('A', crearGlifo("01110", "10001", "10001", "11111", "10001", "10001", "10001"));
        glifos.put('B', crearGlifo("11110", "10001", "10001", "11110", "10001", "10001", "11110"));
        glifos.put('C', crearGlifo("01110", "10001", "10000", "10000", "10000", "10001", "01110"));
        glifos.put('D', crearGlifo("11100", "10010", "10001", "10001", "10001", "10010", "11100"));
        glifos.put('E', crearGlifo("11111", "10000", "10000", "11110", "10000", "10000", "11111"));
        glifos.put('F', crearGlifo("11111", "10000", "10000", "11110", "10000", "10000", "10000"));
        glifos.put('G', crearGlifo("01110", "10001", "10000", "10111", "10001", "10001", "01110"));
        glifos.put('H', crearGlifo("10001", "10001", "10001", "11111", "10001", "10001", "10001"));
        glifos.put('I', crearGlifo("11111", "00100", "00100", "00100", "00100", "00100", "11111"));
        glifos.put('J', crearGlifo("00111", "00010", "00010", "00010", "10010", "10010", "01100"));
        glifos.put('K', crearGlifo("10001", "10010", "10100", "11000", "10100", "10010", "10001"));
        glifos.put('L', crearGlifo("10000", "10000", "10000", "10000", "10000", "10000", "11111"));
        glifos.put('M', crearGlifo("10001", "11011", "10101", "10001", "10001", "10001", "10001"));
        glifos.put('N', crearGlifo("10001", "11001", "10101", "10011", "10001", "10001", "10001"));
        glifos.put('O', crearGlifo("01110", "10001", "10001", "10001", "10001", "10001", "01110"));
        glifos.put('P', crearGlifo("11110", "10001", "10001", "11110", "10000", "10000", "10000"));
        glifos.put('Q', crearGlifo("01110", "10001", "10001", "10001", "10101", "10010", "01101"));
        glifos.put('R', crearGlifo("11110", "10001", "10001", "11110", "10100", "10010", "10001"));
        glifos.put('S', crearGlifo("01111", "10000", "10000", "01110", "00001", "00001", "11110"));
        glifos.put('T', crearGlifo("11111", "00100", "00100", "00100", "00100", "00100", "00100"));
        glifos.put('U', crearGlifo("10001", "10001", "10001", "10001", "10001", "10001", "01110"));
        glifos.put('V', crearGlifo("10001", "10001", "10001", "10001", "10001", "01010", "00100"));
        glifos.put('W', crearGlifo("10001", "10001", "10001", "10101", "10101", "10101", "01010"));
        glifos.put('X', crearGlifo("10001", "10001", "01010", "00100", "01010", "10001", "10001"));
        glifos.put('Y', crearGlifo("10001", "10001", "01010", "00100", "00100", "00100", "00100"));
        glifos.put('Z', crearGlifo("11111", "00001", "00010", "00100", "01000", "10000", "11111"));
        glifos.put('0', crearGlifo("01110", "10001", "10011", "10101", "11001", "10001", "01110"));
        glifos.put('1', crearGlifo("00100", "01100", "00100", "00100", "00100", "00100", "01110"));
        glifos.put('2', crearGlifo("01110", "10001", "00001", "00010", "00100", "01000", "11111"));
        glifos.put('3', crearGlifo("11110", "00001", "00001", "01110", "00001", "00001", "11110"));
        glifos.put('4', crearGlifo("00010", "00110", "01010", "10010", "11111", "00010", "00010"));
        glifos.put('5', crearGlifo("11111", "10000", "10000", "11110", "00001", "00001", "11110"));
        glifos.put('6', crearGlifo("01110", "10000", "10000", "11110", "10001", "10001", "01110"));
        glifos.put('7', crearGlifo("11111", "00001", "00010", "00100", "01000", "01000", "01000"));
        glifos.put('8', crearGlifo("01110", "10001", "10001", "01110", "10001", "10001", "01110"));
        glifos.put('9', crearGlifo("01110", "10001", "10001", "01111", "00001", "00001", "01110"));
        glifos.put(':', crearGlifo("00000", "00100", "00100", "00000", "00100", "00100", "00000"));
        glifos.put('/', crearGlifo("00001", "00010", "00100", "00100", "01000", "10000", "00000"));
        glifos.put('!', crearGlifo("00100", "00100", "00100", "00100", "00100", "00000", "00100"));
        glifos.put('-', crearGlifo("00000", "00000", "00000", "11111", "00000", "00000", "00000"));
        glifos.put(' ', crearGlifo("00000", "00000", "00000", "00000", "00000", "00000", "00000"));
    }

    private Sprite crearGlifo(String... filas) {
        Sprite s = new Sprite();
        for (int y = 0; y < filas.length; y++) {
            String row = filas[y];
            for (int x = 0; x < row.length(); x++) {
                if (row.charAt(x) == '1') {
                    s.agregarPixel(x, y, new float[] { 1f, 1f, 1f });
                }
            }
        }
        return s;
    }
}
