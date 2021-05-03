package org.openjfx.model.datamodel;

import java.time.LocalDateTime;

public class PaperFile {
    private String fileName;
    private String storagePath;
    private String creationDate;

    public PaperFile(String fileName, String storagePath){
        this.fileName= fileName;
        this.storagePath = storagePath;
        this.creationDate = LocalDateTime.now().toString();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
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
