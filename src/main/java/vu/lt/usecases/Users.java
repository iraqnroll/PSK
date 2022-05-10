package vu.lt.usecases;

import lombok.Getter;
import lombok.Setter;
import vu.lt.entities.Customer;
import vu.lt.entities.Employee;
import vu.lt.persistence.CustomerDAO;
import vu.lt.persistence.EmployeeDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class Users {

    @Inject
    private CustomerDAO customerDAO;
    @Inject
    private EmployeeDAO employeeDAO;

    @Getter
    @Setter
    private Customer customerToCreate = new Customer();

    @Getter
    @Setter
    private Employee employeeToCreate = new Employee();

    @Getter
    private List<Customer> allCustomers;
    @Getter
    private  List<Employee> allEmployees;

    @PostConstruct
    public void init(){
        loadAllUsers();
    }

    private void loadAllUsers() {
        this.allCustomers = customerDAO.findAll();
        this.allEmployees = employeeDAO.findAll();
    }

    @Transactional
    public String createCustomer(){
        this.customerDAO.create(customerToCreate);
        return "index?faces-redirect=true";
    }

    @Transactional
    public String createEmployee(){
        this.employeeDAO.create(employeeToCreate);
        return "index?faces-redirect=true";
    }
}
