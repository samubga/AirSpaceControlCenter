package com.example.airspacecontrolcenter.model;

import com.example.airspacecontrolcenter.model.Ciudad;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

import java.util.List;

public class InfoAirplane {
    private String nombre;
    private DefaultWaypoint df;
    private Ciudad ciudadDestino;

    private Ciudad ciudadOrigen;

    private double speed;
    private double destinationLat;
    private double destinationLong;



    private Routes ruta;

    private boolean permisoDespegue = true;

    public List<GeoPosition> getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(List<GeoPosition> coordenadas) {
        this.coordenadas = coordenadas;
    }

    private List<GeoPosition> coordenadas;

    public InfoAirplane(String nombre, DefaultWaypoint df, double destinationLat, double destinationLong,double speed) {
        this.nombre = nombre;
        this.df = df;
        this.destinationLat = destinationLat;
        this.destinationLong = destinationLong;
        this.speed = speed;
    }

    public InfoAirplane(String nombre, DefaultWaypoint df, double destinationLat, double destinationLong,double speed, Ciudad ciudadOrigen, Ciudad ciudadDestino, Routes ruta) {
        this.nombre = nombre;
        this.df = df;
        this.destinationLat = destinationLat;
        this.destinationLong = destinationLong;
        this.speed = speed;
        this.ciudadOrigen=ciudadOrigen;
        this.ciudadDestino=ciudadDestino;
        this.ruta = ruta;
    }

    public Routes getRuta() {
        return ruta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public DefaultWaypoint getDf() {
        return df;
    }

    public void setDf(DefaultWaypoint df) {
        this.df = df;
    }

    public double getDestinationLat() {
        return destinationLat;
    }

    public void setDestinationLat(double destinationLat) {
        this.destinationLat = destinationLat;
    }

    public double getDestinationLong() {
        return destinationLong;
    }

    public void setDestinationLong(double destinationLong) {
        this.destinationLong = destinationLong;
    }
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public GeoPosition getPosition(){
        GeoPosition gp = new GeoPosition(destinationLat,destinationLong);
        return gp;

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

    public boolean isPermisoDespegue() {
        return permisoDespegue;
    }

    public void setPermisoDespegue(boolean permisoDespegue) {
        this.permisoDespegue = permisoDespegue;
    }
}

