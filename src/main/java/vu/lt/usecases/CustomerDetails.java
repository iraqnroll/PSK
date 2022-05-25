package vu.lt.usecases;

import lombok.Getter;
import lombok.Setter;
import vu.lt.entities.Customer;
import vu.lt.entities.Package;
import vu.lt.persistence.CustomerDAO;
import vu.lt.persistence.IPackageDAO;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Model
@ViewScoped
public class CustomerDetails implements Serializable {

    @Inject
    private CustomerDAO customerDAO;
    @Inject
    private IPackageDAO packageDAO;

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

    @Getter
    @Setter
    private String oleMessage;

    @Getter
    @Setter
    private Long receivedPkgCount;

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
        receivedPkgCount = packageDAO.countPackages(customerId);
    }

    @PreDestroy
    public void destroyed(){
        System.out.println("CustomerDetails bean is about to be destroyed.");
    }

    @Transactional
    public String updateCustomer(){
        try{
            customerDAO.update(customer);
        }
        catch (OptimisticLockException ole){
            return "customer?faces-redirect=true&customerId=" + customer.getId() + "&error=optimistic-lock-exception";
        }
        this.oleMessage = "";
        return "customer?faces-redirect=true&customerId=" + customer.getId();
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
