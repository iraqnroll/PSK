package vu.lt.persistence;

import vu.lt.entities.Employee;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class EmployeeDAO {

    @Inject
    private EntityManager em;

    public void create(Employee employee){
        em.persist(employee);
    }

    public Employee update(Employee employee){
        return em.merge(employee);
    }

    public List<Employee> findAll(){
        return em.createNamedQuery("Employee.findAll", Employee.class)
                .getResultList();
    }

    public Employee findById(Integer id){
        return em.find(Employee.class, id);
    }

}
