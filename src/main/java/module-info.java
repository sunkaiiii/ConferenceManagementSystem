module untitled2{
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    exports org.openjfx;
    exports org.openjfx.controllers.pagemodel;
    exports org.openjfx.model.datamodel;
    exports org.openjfx.model.datamodel.interfaces;
    opens org.openjfx.controllers.pagemodel to javafx.fxml;
    exports org.openjfx.controllers.page;
    opens org.openjfx.controllers.page to javafx.fxml;
    opens org.openjfx.model.datamodel.abstracts to com.google.gson;
    opens org.openjfx.model.datamodel to com.google.gson;
}
