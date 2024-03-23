package com.example.airspacecontrolcenter.model;

import org.jxmapviewer.viewer.GeoPosition;

import java.util.ArrayList;
import java.util.List;

public class Routes {


    private Ciudad ciudadOrigen;
    private Ciudad ciudadDestino;



    private List<GeoPosition> coordenadas;

    public Routes(Ciudad ciudadOrigen, Ciudad ciudadDestino) {
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        this.coordenadas = new ArrayList<>();
        coordenadas.add(ciudadDestino.getPosicion());
    }

    public void agregarPosicionEntreOrigenYDestino(GeoPosition coordenada) {
        coordenadas.add(coordenada);

        //Agregar la ciudad de destino a la ultima posicion
        coordenadas.remove(ciudadDestino.getPosicion());
        coordenadas.add(ciudadDestino.getPosicion());
    }

    public List<GeoPosition> getListaCoordenadas() {
        return coordenadas;
    }

    public GeoPosition getCoordenada(int n){
        return coordenadas.get(n);
    }

    public Ciudad getCiudadOrigen() {
        return ciudadOrigen;
    }

    public void setCiudadOrigen(Ciudad ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    public Ciudad getCiudadDestino() {
        return ciudadDestino;
    }

    public void setCiudadDestino(Ciudad ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }


}
