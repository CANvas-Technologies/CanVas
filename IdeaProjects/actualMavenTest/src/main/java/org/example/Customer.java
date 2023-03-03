package org.example;
import org.example.DataTransferObject;

import javax.xml.crypto.Data;

public class Customer implements DataTransferObject {
    private long customerId;
    private String firstName;
    private String lastName;
    public long getId() {
        return customerId;
    }
    public long getCustomerId(){
        return customerId;
    }
    public void setCustomerId(long customerId){
        this.customerId = customerId;
    }
    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", first name='" + firstName + '\'' +
                ", last name ='" + lastName + '\'' +
                '}';
    }
}
