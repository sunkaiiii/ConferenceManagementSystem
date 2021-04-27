package org.openjfx.model.datamodel;

import org.openjfx.model.datamodel.abstracts.User;
import org.openjfx.model.datamodel.interfaces.Author;
import org.openjfx.model.datamodel.interfaces.CSVConvertable;
import org.openjfx.model.datamodel.interfaces.Chair;
import org.openjfx.model.datamodel.interfaces.Reviewer;
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

    @Override
    public String convertToCSVLine() {
        return getAllFields().stream().map(this::getFiledValue).collect(Collectors.joining(","));
    }

    private void findAllDeclaredFields(List<Field> fields,Class<?> clazz){
        var superClass = clazz.getSuperclass();
        if(superClass!=null){
            findAllDeclaredFields(fields,superClass);
        }
        Collections.addAll(fields, clazz.getDeclaredFields());
    }

    private List<Field> getAllFields(){
        List<Field> result = new ArrayList<>();
        findAllDeclaredFields(result,this.getClass());
        return result;
    }


    private String getFiledValue(Field field){
        //TODO handle exception in the outside
        try{
            field.setAccessible(true);
            return field.get(this).toString();
        }catch (Exception ignored){}
        return "";
    }

    public static RegisterdUser readFromCSVString(String csvData) throws IndexOutOfBoundsException {
        String[] data = csvData.split(",");
        return new RegisterdUser(data[0],data[1],data[2],data[3],data[4],data[5],data[6]);
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

    public static void main(String[] args) {
        var user = new RegisterdUser("kai@k.com","password","Kai","Sun","44","AI","details");
        String csvLine = user.convertToCSVLine();
        user = RegisterdUser.readFromCSVString(csvLine);
        System.out.println(user);
        try {
            DatabaseService.getInstance().addNewRecord("user.csv",csvLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
