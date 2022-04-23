module com.example.iss_bun {
    requires javafx.controls;
    requires javafx.fxml;
    opens com.example.iss_bun to javafx.fxml;
    opens com.example.iss_bun.controller to javafx.fxml;
    exports com.example.iss_bun;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;


}