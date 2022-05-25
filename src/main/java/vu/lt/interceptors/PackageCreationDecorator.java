package vu.lt.interceptors;

import vu.lt.entities.Customer;
import vu.lt.entities.Package;
import vu.lt.persistence.IPackageDAO;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

@Decorator
public abstract class PackageCreationDecorator implements IPackageDAO {
    @Inject @Delegate @Any IPackageDAO packageDAO;

    public void create(Package pkg){
        packageDAO.create(pkg);
        Customer sender = pkg.getSender();
        System.out.println(sender.getName()
                + " " +sender.getSurname()
                + " created a new package with ID "
                + pkg.getId().toString());
    }
}
