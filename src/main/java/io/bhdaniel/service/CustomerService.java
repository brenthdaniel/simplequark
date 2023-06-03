package io.bhdaniel.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.bhdaniel.model.Customer;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

@ApplicationScoped
public class CustomerService {
    @Inject
    DynamoDbClient dynamoDB;

    public List<Customer> findAll() {
        List<Customer> customers = dynamoDB.scanPaginator(scanRequest()).items().stream()
                .map(Customer::from)
                .collect(Collectors.toList());
        return customers;        
    }

    public List<Customer> findByAccountID(long accountID) {
        List<Customer> customers = dynamoDB.scanPaginator(scanRequest()).items().stream()
                .map(Customer::from)
                .filter(customer -> customer.getAccountID() == accountID)
                .collect(Collectors.toList());
        return customers;
    }

    public void addCustomer(Customer customer) {
        Log.info("Adding customer: " + customer.getAccountID() + " " + "first: " + customer.getFirstName() + " last: " + customer.getLastName());
        dynamoDB.putItem(putRequest(customer));
    }
    

    protected ScanRequest scanRequest() {
        return ScanRequest.builder().tableName(getTableName())
                .attributesToGet(Customer.CUSTOMER_ACCOUNTID_COLUMN, Customer.CUSTOMER_FIRSTNAME_COLUMN, Customer.CUSTOMER_LASTNAME_COLUMN).build();
    }

    private String getTableName() {
        return "customers";
    }



    protected PutItemRequest putRequest(Customer customer) {
        Map<String, AttributeValue> item = new HashMap<>();
        // todo data type issue
        item.put(Customer.CUSTOMER_ACCOUNTID_COLUMN, AttributeValue.builder().n(String.valueOf(customer.getAccountID())).build());
        item.put(Customer.CUSTOMER_FIRSTNAME_COLUMN, AttributeValue.builder().s(customer.getFirstName()).build());
        item.put(Customer.CUSTOMER_LASTNAME_COLUMN, AttributeValue.builder().s(customer.getLastName()).build());     

        return PutItemRequest.builder()
                .tableName(getTableName())
                .item(item)
                .build();
    }
}
