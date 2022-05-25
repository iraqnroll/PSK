package vu.lt.persistence;

import vu.lt.entities.Customer;
import vu.lt.entities.Employee;
import vu.lt.entities.Package;

import java.util.List;

public interface IPackageDAO {
    public void create(Package pkg);
    public void delete(Package pkg);
    public Package findById(Integer id);
    public List<Package> findByReceiver(Customer customer);
    public List<Package> findBySender(Customer customer);
    public List<Package> findByCourier(Employee employee);
    public Package update(Package pkg);
    public Long countPackages(Integer id);
    public Long countAllPackages();
}
