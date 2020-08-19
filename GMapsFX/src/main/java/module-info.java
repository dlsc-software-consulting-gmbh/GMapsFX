open module com.dlsc.gmapsfx {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.web;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.swing;

    requires jdk.jsobject;
    requires java.logging;
    requires java.desktop;
    requires org.slf4j;
    requires java.xml.bind;

    exports com.dlsc.gmapsfx;
    exports com.dlsc.gmapsfx.javascript;
    exports com.dlsc.gmapsfx.javascript.event;
    exports com.dlsc.gmapsfx.javascript.object;
    exports com.dlsc.gmapsfx.service.directions;
    exports com.dlsc.gmapsfx.service.elevation;
    exports com.dlsc.gmapsfx.service.geocoding;
    exports com.dlsc.gmapsfx.shapes;
    exports com.dlsc.gmapsfx.util;
    exports com.dlsc.gmapsfx.zoom;
}