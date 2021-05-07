package org.openjfx.model;

public class AuthorInformation {
    private String authorDisplayName;
    private String authorIdentifyName;

    public AuthorInformation(String authorDisplayName, String authorIdentifyName) {
        this.authorDisplayName = authorDisplayName;
        this.authorIdentifyName = authorIdentifyName;
    }

    public String getAuthorDisplayName() {
        return authorDisplayName;
    }

    public void setAuthorDisplayName(String authorDisplayName) {
        this.authorDisplayName = authorDisplayName;
    }

    public String getAuthorIdentifyName() {
        return authorIdentifyName;
    }

    public void setAuthorIdentifyName(String authorIdentifyName) {
        this.authorIdentifyName = authorIdentifyName;
    }
}
