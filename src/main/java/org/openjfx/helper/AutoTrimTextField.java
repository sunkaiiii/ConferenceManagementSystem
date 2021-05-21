package org.openjfx.helper;

import javafx.scene.control.TextField;

public class AutoTrimTextField extends TextField {
    public String getTrimText(){
        return getText().trim();
    }
}
