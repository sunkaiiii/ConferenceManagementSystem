package org.openjfx.model;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.time.LocalDateTime;
import java.util.List;

public class PaperFile {
    private String fileName;
    private String[] storagePath;
    private String creationDate;

    public PaperFile(String fileName, String storagePath) {
        this.fileName = fileName;
        this.storagePath = storagePath.split(FileSystems.getDefault().getSeparator());
        this.creationDate = LocalDateTime.now().toString();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStoragePath() {
        return String.join(FileSystems.getDefault().getSeparator(), this.storagePath);
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath.split(FileSystems.getDefault().getSeparator());
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "PaperFile{" +
                "fileName='" + fileName + '\'' +
                ", storagePath='" + storagePath + '\'' +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }
}
