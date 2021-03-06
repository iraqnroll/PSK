package vu.lt.persistence;

import vu.lt.entities.Customer;
import vu.lt.entities.Employee;
import vu.lt.entities.Package;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class PackageDAO implements IPackageDAO {

    @Inject
    protected EntityManager em;

    public void create(Package pkg){
        em.persist(pkg);
    }

    public void delete(Package pkg){
        em.remove(pkg);
    }

    public Package findById(Integer id){
        return em.find(Package.class, id);
    }

    public List<Package> findAll(){
        return em.createNamedQuery("Package.findAll", Package.class)
                .getResultList();
    }

    public List<Package> findByReceiver(Customer customer){
        return em.createNamedQuery("Package.findByReceiver", Package.class)
                .setParameter("receiverId", customer.getId())
                .getResultList();
    }

    public List<Package> findBySender(Customer customer){
        return em.createNamedQuery("Package.findBySender", Package.class)
                .setParameter("senderId",customer.getId())
                .getResultList();
    }

    public List<Package> findByCourier(Employee courier){
        return em.createNamedQuery("Package.findByCourier", Package.class)
                .setParameter("courierId", courier.getId())
                .getResultList();
    }

    public Package update(Package pkg){
        return em.merge(pkg);
    }

    public Long countPackages(Integer Id){
        return em.createNamedQuery("Package.countByReceiver", Long.class)
                .setParameter("receiverId", Id)
                .getSingleResult();
    }

    public Long countAllPackages(){
        return em.createNamedQuery("Package.countAllPackages", Long.class)
                .getSingleResult();
    }
}
