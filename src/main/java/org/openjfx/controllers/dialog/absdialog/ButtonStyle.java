package org.openjfx.controllers.dialog.absdialog;

import javafx.scene.paint.Paint;

public class ButtonStyle {
    private String buttonText;
    private Paint buttonTextFill;
    private String buttonBackground;

    public ButtonStyle() {
    }

    public ButtonStyle(String buttonText, String buttonTextFill, String buttonBackground) {
        this.buttonText = buttonText;
        this.buttonTextFill = Paint.valueOf(buttonTextFill);
        this.buttonBackground = buttonBackground;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public Paint getButtonTextFill() {
        return buttonTextFill;
    }

    public void setButtonTextFill(Paint buttonTextFill) {
        this.buttonTextFill = buttonTextFill;
    }

    public String getButtonBackground() {
        return buttonBackground;
    }

    public void setButtonBackground(String buttonBackground) {
        this.buttonBackground = buttonBackground;
    }
}
