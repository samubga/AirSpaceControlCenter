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

    opens com.example.airspacecontrolcenter to javafx.fxml, org.hibernate.orm.core;

    exports com.example.airspacecontrolcenter;
}