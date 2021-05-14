package org.openjfx.helper;

import org.openjfx.model.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

public class FileHelper {
    public final static String PAPER_FOLDER = "papers";
    private static final FileHelper Instance = new FileHelper();

    private FileHelper() {

    }

    public File uploadFileToServer(String paperName, String originalAbsolutePath) throws IOException {
        if (!Files.exists(Path.of(PAPER_FOLDER))) {
            if (!new java.io.File(PAPER_FOLDER).mkdir()) {
                throw new IOException("Cannot create paper folder");
            }
        }

        Path copied = Paths.get(PAPER_FOLDER, UUID.randomUUID() + "." + originalAbsolutePath.substring(originalAbsolutePath.lastIndexOf(".") + 1));
        String storedPath = copied.toString();
        Path originalPath = Paths.get(originalAbsolutePath);
        Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
        return new File(paperName, storedPath);
    }

    public Optional<String> getFileExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }


    public static FileHelper getInstance() {
        return Instance;
    }
}
