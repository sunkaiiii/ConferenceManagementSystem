package org.openjfx.model;

import org.openjfx.helper.CSVConvertHelper;
import org.openjfx.model.abstracts.User;
import org.openjfx.model.factory.DataModelFactory;
import org.openjfx.model.interfaces.Author;
import org.openjfx.model.interfaces.CSVConvertable;
import org.openjfx.model.interfaces.Chair;
import org.openjfx.model.interfaces.Reviewer;
import org.openjfx.service.DatabaseService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

public class RegisterdUser extends User implements Author, Chair, Reviewer, CSVConvertable<RegisterdUser> {
    private String firstName;
    private String lastName;
    private String highestQualification;
    private Map<String,Integer> interestAreas;
    private String employerDetails;
    private String creationTime;

    public RegisterdUser(String userName, String password, String firstName, String lastName, String highestQualification, Map<String,Integer> interestAreas, String employerDetails) {
        super(userName, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.highestQualification = highestQualification;
        this.interestAreas = interestAreas;
        this.employerDetails = employerDetails;
        this.creationTime = LocalDateTime.now().toString();
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
        return firstName + " " + lastName;
    }

    @Override
    public String getAuthorIdentifiedName() {
        return getUserName();
    }

    public void setInterestAreas(Map<String,Integer> interestAreas) {
        this.interestAreas = interestAreas;
    }

    public String getEmployerDetails() {
        return employerDetails;
    }

    public void setEmployerDetails(String employerDetails) {
        this.employerDetails = employerDetails;
    }

    public Map<String,Integer> getInterestAreas() {
        return interestAreas;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String convertToCSVLine() {
        return CSVConvertHelper.convertClassToCSVStringLine(this);
    }

    @Override
    public String toString() {
        return "RegisterdUser{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", highestQualification='" + highestQualification + '\'' +
                ", interestAreas='" + interestAreas + '\'' +
                ", employerDetails='" + employerDetails + '\'' +
                ", creationTime='" + creationTime + '\'' +
                '}';
    }

    @Override
    public String getChairName() {
        return getUserName();
    }

    public static void main(String[] args) {
        var user = new RegisterdUser("kai@k.com", "password", "Kai", "Sun", "44", Map.of("AI",4), "details");
        String csvLine = user.convertToCSVLine();
        user = DataModelFactory.convertUserFromCSVLine(csvLine);
        System.out.println(user);
        try {
            DatabaseService.getDefaultInstance().addNewRecord(() -> "user.csv", csvLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getReviewerName() {
        return getFirstName() + "." + getLastName().charAt(0);
    }

    @Override
    public String getReviewerIdentifiedName() {
        return getUserName();
    }
}
