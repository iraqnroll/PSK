package vu.lt.usecases;

import lombok.Getter;
import lombok.Setter;
import vu.lt.entities.Employee;
import vu.lt.entities.Package;
import vu.lt.persistence.EmployeeDAO;
import vu.lt.persistence.PackageDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Model
public class EmployeeDetails {
    @Inject
    private EmployeeDAO employeeDAO;
    @Inject
    private PackageDAO packageDAO;

    @Getter
    @Setter
    private Employee currentEmployee;

    @Getter
    @Setter
    private List<Package> packageList;

    @Getter
    @Setter
    private Integer selectedPackageId;

    @PostConstruct
    public void init(){
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer employeeId = Integer.parseInt(requestParameters.get("employeeId"));

        currentEmployee = employeeDAO.findById(employeeId);
        packageList = packageDAO.findAll();
        packageList.removeIf(pack -> pack.getCouriers().contains(currentEmployee));
    }

    @Transactional
    public String setCourier(){
        Package selectedPackage = packageDAO.findById(selectedPackageId);
        List<Package> currentDeliveries = currentEmployee.getDeliveries();
        currentDeliveries.add(selectedPackage);
        currentEmployee.setDeliveries(currentDeliveries);
        employeeDAO.update(currentEmployee);
        return "employee?faces-redirect=true&employeeId=" + currentEmployee.getId();
    }
}
