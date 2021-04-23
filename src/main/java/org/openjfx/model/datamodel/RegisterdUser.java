package org.openjfx.model.datamodel;

import org.openjfx.model.datamodel.abstracts.User;
import org.openjfx.model.datamodel.interfaces.Author;
import org.openjfx.model.datamodel.interfaces.Chair;
import org.openjfx.model.datamodel.interfaces.Reviewer;

public class RegisterdUser extends User implements Author, Chair, Reviewer {
    private String firstName;
    private String lastName;
    private String highestQualification;
    private String interestArea;
    private String employerDetails;

    public RegisterdUser(String userName, String password, String firstName, String lastName, String highestQualification, String interestArea, String employerDetails) {
        super(userName, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.highestQualification = highestQualification;
        this.interestArea = interestArea;
        this.employerDetails = employerDetails;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHighestQualification() {
        return highestQualification;
    }

    public void setHighestQualification(String highestQualification) {
        this.highestQualification = highestQualification;
    }

    public String getInterestArea() {
        return interestArea;
    }

    public void setInterestArea(String interestArea) {
        this.interestArea = interestArea;
    }

    public String getEmployerDetails() {
        return employerDetails;
    }

    public void setEmployerDetails(String employerDetails) {
        this.employerDetails = employerDetails;
    }
}
