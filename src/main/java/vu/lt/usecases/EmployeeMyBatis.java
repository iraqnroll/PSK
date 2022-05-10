package vu.lt.usecases;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Param;
import vu.lt.mybatis.dao.EmployeeMapper;
import vu.lt.mybatis.dao.PackageMapper;
import vu.lt.mybatis.model.Employee;
import vu.lt.mybatis.model.Package;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Model
public class EmployeeMyBatis {
    @Inject private EmployeeMapper employeeMapper;
    @Inject private PackageMapper packageMapper;

    @Getter
    @Setter
    private Employee currentEmployee;

    @Getter
    @Setter
    private Integer deliveryToDeleteId;

    @Getter
    @Setter
    private Integer deliveryToAddId;

    @Getter
    @Setter
    private List<Package> availableDeliveries;


    @PostConstruct
    public void init(){
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer employeeId = Integer.parseInt(requestParameters.get("employeeId"));
        currentEmployee = employeeMapper.selectByPrimaryKey(employeeId);
        availableDeliveries = packageMapper.selectAvailableDeliveries();
        currentEmployee.setDeliveries(employeeMapper.selectDeliveriesForEmployee(currentEmployee.getId()));
    }

    public List<Package> getAllDeliveries() {
        return employeeMapper.selectDeliveriesForEmployee(currentEmployee.getId());
    }

    @Transactional
    public String deleteDelivery(){
        employeeMapper.deleteDeliveryForEmployee(currentEmployee.getId(), deliveryToDeleteId);
        return "/myBatis/deliveries?faces-redirect=true&employeeId=" + currentEmployee.getId();
    }

    @Transactional
    public String createNewDelivery(){
        employeeMapper.insertNewDeliveryForEmployee(currentEmployee.getId(), deliveryToAddId);
        return "/myBatis/deliveries?faces-redirect=true&employeeId=" + currentEmployee.getId();
    }
}
