package org.openjfx.controllers.page.abspage;

import javafx.scene.image.Image;
import org.openjfx.helper.FileHelper;

import java.util.Optional;

public abstract class AbstractFileListCell {
    public static final String WORD_RESOURCE = "/org/openjfx/images/word.png";
    public static final String PNG_RESOURCE = "/org/openjfx/images/pdf.png";
    private static final String WORD_FORMAT_OLD = "doc";
    private static final String WORD_FORMAT = "docx";
    private static final String PDF_FORMAT = "pdf";

    protected Image getFileIcon(String storagePath) {
        Optional<String> fileExtension = FileHelper.getInstance().getFileExtensionByStringHandling(storagePath);
        Image result = null;
        if (fileExtension.isPresent()) {
            switch (fileExtension.get()) {
                case WORD_FORMAT:
                case WORD_FORMAT_OLD:
                    result = new Image(getClass().getResource(WORD_RESOURCE).toString());
                    break;
                case PDF_FORMAT:
                    result = new Image(getClass().getResource(PNG_RESOURCE).toString());
                    break;
            }
        }
        return result;
    }
}
