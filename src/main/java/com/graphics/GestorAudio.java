package com.graphics;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

public class GestorAudio {
    private static final String AUDIO_WING = "/audio/wing.wav";
    private static final String AUDIO_POINT = "/audio/point.wav";
    private static final String AUDIO_HIT = "/audio/hit.wav";
    private static final String AUDIO_DIE = "/audio/die.wav";
    private static final String AUDIO_SWOOSH = "/audio/swoosh.wav";

    private final List<Clip> clipsActivos;
    private boolean habilitado;

    public GestorAudio() {
        this.clipsActivos = new ArrayList<>();
        this.habilitado = true;
    }

    public void reproducirSalto() {
        reproducir(AUDIO_WING);
    }

    public void reproducirPunto() {
        reproducir(AUDIO_POINT);
    }

    public void reproducirGolpe() {
        reproducir(AUDIO_HIT);
    }

    public void reproducirMuerte() {
        reproducir(AUDIO_DIE);
    }

    public void reproducirTransicion() {
        reproducir(AUDIO_SWOOSH);
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public void limpiar() {
        synchronized (clipsActivos) {
            for (Clip clip : clipsActivos) {
                clip.stop();
                clip.close();
            }
            clipsActivos.clear();
        }
    }

    private void reproducir(String ruta) {
        if (!habilitado) {
            return;
        }

        liberarClipsCerrados();

        try (InputStream input = GestorAudio.class.getResourceAsStream(ruta)) {
            if (input == null) {
                throw new IllegalStateException("No se encontro el audio: " + ruta);
            }

            try (AudioInputStream audio = AudioSystem.getAudioInputStream(new BufferedInputStream(input))) {
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                clip.addLineListener(evento -> onEventoClip(clip, evento));
                synchronized (clipsActivos) {
                    clipsActivos.add(clip);
                }
                clip.start();
            }
        } catch (Exception e) {
            throw new RuntimeException("No se pudo reproducir el audio: " + ruta, e);
        }
    }

    private void onEventoClip(Clip clip, LineEvent evento) {
        if (evento.getType() == LineEvent.Type.STOP) {
            clip.close();
            synchronized (clipsActivos) {
                clipsActivos.remove(clip);
            }
        }
    }

    private void liberarClipsCerrados() {
        synchronized (clipsActivos) {
            Iterator<Clip> iterator = clipsActivos.iterator();
            while (iterator.hasNext()) {
                Clip clip = iterator.next();
                if (!clip.isOpen()) {
                    iterator.remove();
                }
            }
        }
    }
}
