package vu.lt.persistence;

import vu.lt.entities.Package;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Specializes;

@Alternative
@ApplicationScoped
public class ExtendedPackageDAO extends PackageDAO{
    @Override
    public Long countPackages(Integer Id){
        return em.createNamedQuery("Package.countBySender", Long.class)
                .setParameter("senderId", Id)
                .getSingleResult();
    }
}
