package vu.lt.persistence;

import vu.lt.entities.Customer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class CustomerDAO {

    @Inject
    private EntityManager em;

    public void create(Customer customer){
        em.persist(customer);
    }

    public Customer update(Customer customer){
        return em.merge(customer);
    }

    public List<Customer> findAll(){
        return em.createNamedQuery("Customer.findAll", Customer.class)
                .getResultList();
    }

    public Customer findById(Integer id){
        return em.find(Customer.class, id);
    }
}
