package org.openjfx.helper;

import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

public final class ViewHelper {
    public static Border createGeneralDashBolder(String colour){
        CornerRadii radii = new CornerRadii(8);
        BorderWidths widths = new BorderWidths(2);
        return new Border(new BorderStroke(Paint.valueOf(colour), BorderStrokeStyle.DASHED, radii,widths));
    }
}
