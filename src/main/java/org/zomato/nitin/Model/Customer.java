package org.zomato.nitin.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

@Document(collection = "customer")
public class Customer {
    @Id
    @JsonProperty("customerId")
    private String customerId;
    @JsonProperty("customerName")
    private String customerName;
    @JsonProperty("customerAddress")
    private String customerAddress;
    @JsonProperty("customerPhone")
    private String customerPhone;
    @JsonProperty("myOrders")
    private List<String> myOrdersList;

    public Customer(){
        super();
    }
    // Getters and Setters

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public List<String> getMyOrdersList() {
        return myOrdersList;
    }

    public void setMyOrdersList(List<String> myOrdersList) {
        this.myOrdersList = myOrdersList;
    }


    // Constructor, toString(), equals(), hashCode()

    public Customer(String customerId, String customerName, String customerAddress, String customerPhone, List<String> myOrdersList) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPhone = customerPhone;
        this.myOrdersList = myOrdersList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(customerId, customer.customerId) && Objects.equals(customerName, customer.customerName) && Objects.equals(customerAddress, customer.customerAddress) && Objects.equals(customerPhone, customer.customerPhone) && Objects.equals(myOrdersList, customer.myOrdersList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, customerName, customerAddress, customerPhone, myOrdersList);
    }
}
