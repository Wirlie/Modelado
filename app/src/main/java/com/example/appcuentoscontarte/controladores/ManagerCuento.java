package com.example.appcuentoscontarte.controladores;

import android.util.Log;

import java.util.List;

public class ManagerCuento {

    private TipoCuento cuento;
    private IControlador controlador;
    private List<PasoImagen> pasos;
    private PasoImagen pasoActual;
    private int i = 0;

    public ManagerCuento(TipoCuento cuento) {
        this.cuento = cuento;
        iniciar();
    }

    private void iniciar() {
        switch (cuento) {
            case CAPERUCITA:
                controlador = new ControladorCaperucita();
                break;
            case POLLITO_CURIOSO:
                controlador = new ControladorPollitoCurioso();
                break;
        }

        pasos = controlador.getPasos();
        pasoActual = pasos.get(0);
    }

    public TipoCuento getTipoCuento() {
        return cuento;
    }

    public boolean debeCambiarImagen() {
        return pasoActual.siguientePaso();
    }

    public boolean cambiarImagen() {
        if(pasoActual.siguientePaso()){
            i++;
            if(terminar()) return true;

            pasoActual = pasos.get(i);
            return true;
        }

        return false;
    }

    public boolean terminar() {
        Log.d("TERMINAR->", "I=" + i + " | " + pasos.size());
        return i >= pasos.size();
    }
}
