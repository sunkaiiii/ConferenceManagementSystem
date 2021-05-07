module untitled2{
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires tornadofx.controls;
    requires commons.validator;
    exports org.openjfx;
    exports org.openjfx.model;
    exports org.openjfx.model.interfaces;
    exports org.openjfx.controllers.page;
    opens org.openjfx.controllers.page to javafx.fxml;
    opens org.openjfx.model.abstracts to com.google.gson;
    opens org.openjfx.model to com.google.gson;
}
