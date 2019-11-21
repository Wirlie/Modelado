package com.example.appcuentoscontarte.controladores;

import java.util.Arrays;
import java.util.List;

public class ControladorPollitoCurioso implements IControlador {
    @Override
    public List<PasoImagen> getPasos() {
        return Arrays.asList(
                new PasoImagen(1),
                new PasoImagen(1),
                new PasoImagen(1)
        );
    }
}
