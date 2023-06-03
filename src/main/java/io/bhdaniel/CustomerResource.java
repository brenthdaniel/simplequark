package io.bhdaniel;

import java.util.List;

import io.bhdaniel.model.Customer;
import io.bhdaniel.service.CustomerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/customer")
public class CustomerResource {
    @Inject
    CustomerService customerService;

    @GET
    @Path("/findAll")
    public List<Customer> findAll() {
        return customerService.findAll();
    }

    @GET
    @Path("/findByAccount/{accountID}")
    public List<Customer> findByAccountID(long id) {
        return customerService.findByAccountID(id);
    }

    @POST
    public void addCustomer(Customer c) {
        customerService.addCustomer(c);
    }
}
