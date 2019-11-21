package com.example.appcuentoscontarte.controladores;

public class PasoImagen {

    private int iteracionesAudio;
    private int iteracionActualAudio = 0;

    public PasoImagen(int iteracionesAudio) {
        this.iteracionesAudio = iteracionesAudio;
    }

    public boolean siguientePaso() {
        iteracionActualAudio++;
        return iteracionActualAudio >= iteracionesAudio;
    }

}
