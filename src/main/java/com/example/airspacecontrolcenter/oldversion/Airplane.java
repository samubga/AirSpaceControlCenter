package com.example.airspacecontrolcenter.oldversion;

import javafx.scene.image.ImageView;

public class Airplane {
    private ImageView imageView;

    private String nombre;
    private double destinationX;
    private double destinationY;
    private double speed;

    public Airplane(ImageView imageView, double destinationX, double destinationY, double speed) {
        this.imageView = imageView;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.speed=speed;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public double getDestinationX() {
        return destinationX;
    }

    public double getDestinationY() {
        return destinationY;
    }

    public double getX() {
        return imageView.getX();
    }

    public double getY() {
        return imageView.getY();
    }

    public double getSpeed() {
        return speed;
    }

    public void setX(double x) {
        imageView.setX(x);
    }

    public void setY(double y) {
        imageView.setY(y);
    }
}
