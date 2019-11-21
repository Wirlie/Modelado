package com.example.appcuentoscontarte.controladores;

import java.util.Arrays;
import java.util.List;

public class ControladorCaperucita implements IControlador {

    @Override
    public List<PasoImagen> getPasos() {
        return Arrays.asList(
                new PasoImagen(3),
                new PasoImagen(3),
                new PasoImagen(3),
                new PasoImagen(3),
                new PasoImagen(3),
                new PasoImagen(4),
                new PasoImagen(4)
        );
    }

}
