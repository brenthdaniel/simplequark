package io.bhdaniel.model;

import java.util.Map;

import io.quarkus.runtime.annotations.RegisterForReflection;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

// really needed? 
@RegisterForReflection
public class Customer {
    private long accountID;
    private String firstName;
    private String lastName;

    public static final String CUSTOMER_ACCOUNTID_COLUMN = "accountID";
    public static final String CUSTOMER_FIRSTNAME_COLUMN = "first";
    public static final String CUSTOMER_LASTNAME_COLUMN = "last";

    public long getAccountID() {
        return accountID;
    }

    public static Customer from(Map<String, AttributeValue> item) {

        Customer customer = new Customer();
        if (item != null && !item.isEmpty()) {
            // TODO figure out dynamoDB data types
            long id = Long.valueOf(item.get(CUSTOMER_ACCOUNTID_COLUMN).n());
            customer.setAccountID(id);
            customer.setFirstName(item.get(CUSTOMER_FIRSTNAME_COLUMN).s());       
            customer.setLastName(item.get(CUSTOMER_LASTNAME_COLUMN).s());   
        }
        return customer;
    }

    public void setAccountID(long accountID) {
        this.accountID = accountID;
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


    public Customer() {

    }
    
}