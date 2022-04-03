package vu.lt.usecases;

import lombok.Getter;
import lombok.Setter;
import vu.lt.entities.Customer;
import vu.lt.persistence.CustomerDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class Customers {

    @Inject
    private CustomerDAO customerDAO;

    @Getter
    @Setter
    private Customer customerToCreate = new Customer();

    @Getter
    private List<Customer> allCustomers;

    @PostConstruct
    public void init(){
        loadAllCustomers();
    }

    private void loadAllCustomers() {
        this.allCustomers = customerDAO.findAll();
    }

    @Transactional
    public String createCustomer(){
        this.customerDAO.create(customerToCreate);
        return "index?faces-redirect=true";
    }
}
