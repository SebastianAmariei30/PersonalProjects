module ro.ubbcluj.map.demogui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens ro.ubbcluj.map.demogui to javafx.fxml;
    exports ro.ubbcluj.map.demogui;
    opens ro.ubbcluj.map.demogui.domain to javafx.base;
    exports ro.ubbcluj.map.demogui.utils.controller;
    opens ro.ubbcluj.map.demogui.utils.controller to javafx.fxml;
}