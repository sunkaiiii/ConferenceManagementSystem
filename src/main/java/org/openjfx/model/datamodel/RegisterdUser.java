package org.openjfx.model.datamodel;

import org.openjfx.helper.CSVConvertHelper;
import org.openjfx.model.datamodel.abstracts.User;
import org.openjfx.model.datamodel.factory.DataModelFactory;
import org.openjfx.model.datamodel.interfaces.Author;
import org.openjfx.model.datamodel.interfaces.CSVConvertable;
import org.openjfx.model.datamodel.interfaces.Chair;
import org.openjfx.model.datamodel.interfaces.Reviewer;
import org.openjfx.service.ConferenceService;
import org.openjfx.service.DatabaseService;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RegisterdUser extends User implements Author, Chair, Reviewer, CSVConvertable<RegisterdUser> {
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

    @Override
    public boolean isAdmin() {
        return false;
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

    @Override
    public String getAuthorName() {
        return firstName + " "+ lastName;
    }

    @Override
    public String  getAuthorIdentifiedName() {
        return getUserName();
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

    @Override
    public String convertToCSVLine() {
        return CSVConvertHelper.convertClassToCSVStringLine(this);
    }

    @Override
    public String toString() {
        return super.toString() + "RegisterdUser{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", highestQualification='" + highestQualification + '\'' +
                ", interestArea='" + interestArea + '\'' +
                ", employerDetails='" + employerDetails + '\'' +
                '}';
    }

    @Override
    public String getChairName() {
        return getUserName();
    }

    public static void main(String[] args) {
        var user = new RegisterdUser("kai@k.com","password","Kai","Sun","44","AI","details");
        String csvLine = user.convertToCSVLine();
        user = DataModelFactory.convertUserFromCSVLine(csvLine);
        System.out.println(user);
        try {
            DatabaseService.getInstance().addNewRecord("user.csv",csvLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
