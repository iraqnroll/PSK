package vu.lt.usecases;

import lombok.Getter;
import lombok.Setter;
import vu.lt.entities.Package;
import vu.lt.persistence.PackageDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.Map;

@Model
public class PackageDetails {

    @Inject
    private PackageDAO packageDAO;

    @Getter
    @Setter
    private Package currentPackage;

    @PostConstruct
    public void init(){
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer packageId = Integer.parseInt(requestParameters.get("packageId"));

        currentPackage = packageDAO.findById(packageId);
    }
}
