package org.openjfx.helper;

import javafx.scene.control.TextField;

/**
 * A new method has been added to the TextField to get the text of this TextField and apply the trim method
 */
public class AutoTrimTextField extends TextField {
    public String getTrimText(){
        return getText().trim();
    }
}
