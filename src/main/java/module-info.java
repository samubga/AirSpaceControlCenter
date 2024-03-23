module com.example.airspacecontrolcenter {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires org.jxmapviewer.jxmapviewer2;
    requires javafx.swing;
    requires javafx.web;

    opens com.example.airspacecontrolcenter to javafx.fxml, org.hibernate.orm.core;

    exports com.example.airspacecontrolcenter;
    exports com.example.airspacecontrolcenter.oldversion;
    opens com.example.airspacecontrolcenter.oldversion to javafx.fxml, org.hibernate.orm.core;
    exports com.example.airspacecontrolcenter.util;
    opens com.example.airspacecontrolcenter.util to javafx.fxml, org.hibernate.orm.core;
    exports com.example.airspacecontrolcenter.model;
    opens com.example.airspacecontrolcenter.model to javafx.fxml, org.hibernate.orm.core;
    exports com.example.airspacecontrolcenter.controllers;
    opens com.example.airspacecontrolcenter.controllers to javafx.fxml, org.hibernate.orm.core;
}