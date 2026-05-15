package com.graphics.formas;

public class SpriteAnimado {
    private final Sprite[] frames;

    public SpriteAnimado(Sprite... frames) {
        this.frames = frames;
    }

    public Sprite getFrame(int idx) {
        if (idx < 0 || idx >= frames.length) {
            return frames[0];
        }
        return frames[idx];
    }
}
