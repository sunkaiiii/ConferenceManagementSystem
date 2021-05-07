package org.openjfx.helper;

import org.openjfx.model.PaperFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileHelper {
    public final static String PAPER_FOLDER = "papers";
    private static final FileHelper Instance = new FileHelper();

    private FileHelper() {

    }

    public PaperFile uploadFileToServer(String paperName, String originalAbsolutePath) throws IOException {
        if (!Files.exists(Path.of(PAPER_FOLDER))) {
            if (!new File(PAPER_FOLDER).mkdir()) {
                throw new IOException("Cannot create paper folder");
            }
        }

        Path copied = Paths.get(PAPER_FOLDER, UUID.randomUUID() + "." + originalAbsolutePath.substring(originalAbsolutePath.lastIndexOf(".") + 1));
        String storedPath = copied.toString();
        Path originalPath = Paths.get(originalAbsolutePath);
        Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
        return new PaperFile(paperName, storedPath);
    }


    public static FileHelper getInstance() {
        return Instance;
    }
}
