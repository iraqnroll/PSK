package vu.lt.usecases;

import lombok.Getter;
import lombok.Setter;
import vu.lt.entities.Customer;
import vu.lt.entities.Package;
import vu.lt.persistence.CustomerDAO;
import vu.lt.persistence.PackageDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Model
public class CustomerDetails {

    @Inject
    private CustomerDAO customerDAO;
    @Inject
    private PackageDAO packageDAO;

    @Getter
    @Setter
    private Customer customer;

    @Getter
    @Setter
    private Integer receiverId;

    @Getter
    private List<Customer> receiverList;

    @Getter
    @Setter
    private Float packageToSendWeight;

    @Getter
    @Setter
    private String packageToSendShipAddress;

    @Getter
    @Setter
    private Integer packageToCancelId;

    @PostConstruct
    public void init(){
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer customerId = Integer.parseInt(requestParameters.get("customerId"));

        this.customer = customerDAO.findById(customerId);
        this.customer.setPackagesToReceive(packageDAO.findByReceiver(this.customer));
        this.customer.setPackagesToSend(packageDAO.findBySender(this.customer));
        this.receiverList = customerDAO.findAll();
        //remove the current customer from the receiver list.
        this.receiverList.removeIf(cust -> cust.getId().equals(this.customer.getId()));
    }

    @Transactional
    public String sendPackage(){
        var receiver = customerDAO.findById(this.receiverId);
        var packageToSend = new Package(this.packageToSendWeight, this.customer, receiver, this.packageToSendShipAddress);
        this.packageDAO.create(packageToSend);
        return "customer?faces-redirect=true&customerId=" + customer.getId();
    }

    @Transactional
    public String cancelPackage(){
        Package packageToDelete = packageDAO.findById(packageToCancelId);
        packageDAO.delete(packageToDelete);
        return "customer?faces-redirect=true&customerId=" + customer.getId();
    }
}
