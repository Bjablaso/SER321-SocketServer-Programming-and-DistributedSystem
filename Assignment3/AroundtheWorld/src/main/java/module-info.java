module travelaround.aroundtheworld {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.gluonhq.charm.glisten;
    requires org.json;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;

    opens travelaround.aroundtheworld to javafx.fxml;
    exports travelaround.aroundtheworld;
    exports AppLogic;
    opens AppLogic to javafx.fxml;
    exports enitityfolder;
    opens enitityfolder to javafx.fxml;
}