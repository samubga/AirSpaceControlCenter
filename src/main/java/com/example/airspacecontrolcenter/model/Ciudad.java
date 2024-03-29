package com.example.airspacecontrolcenter.model;
import jakarta.persistence.*;
import org.jxmapviewer.viewer.GeoPosition;

@Entity
@Table(name = "Ciudad")
public class Ciudad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private int codigo;



    @Column(name = "nombre")
    private String nombre;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;



    private GeoPosition posicion;

    public Ciudad(String nombre, double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.nombre = nombre;
        posicion = new GeoPosition(latitude, longitude);
    }

    public GeoPosition getPosicion() {
        return posicion;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
