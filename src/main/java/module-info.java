module assignment{
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires tornadofx.controls;
    requires commons.validator;
    exports org.openjfx;
    exports org.openjfx.model;
    exports org.openjfx.controllers;
    exports org.openjfx.model.interfaces;
    exports org.openjfx.controllers.page;
    exports org.openjfx.controllers.dialog;
    exports org.openjfx.controllers.dialog.absdialog;
    exports org.openjfx.helper;
    exports org.openjfx.controllers.page.interfaces;
    opens org.openjfx.helper to javafx.fxml;
    opens org.openjfx.controllers.dialog.absdialog to javafx.fxml;
    opens org.openjfx.controllers.page.abspage to javafx.fxml;
    opens  org.openjfx.controllers.dialog to javafx.fxml;
    opens org.openjfx.controllers.page to javafx.fxml;
    opens org.openjfx.model.abstracts to com.google.gson;
    opens org.openjfx.model to com.google.gson;
}
