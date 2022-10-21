module com.example.puzzlegame
{
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.puzzlegame to javafx.fxml;
    exports com.example.puzzlegame;
}