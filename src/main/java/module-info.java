module untitled2{
    requires javafx.controls;
    requires javafx.fxml;

    opens org.openjfx to javafx.fxml;
    exports org.openjfx;
    exports org.openjfx.controllers.pagemodel;
    opens org.openjfx.controllers.pagemodel to javafx.fxml;
    exports org.openjfx.controllers.page;
    opens org.openjfx.controllers.page to javafx.fxml;
}