package vu.lt.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PACKAGE")
@Getter
@Setter
@EqualsAndHashCode
@NamedQueries({
        @NamedQuery(name = "Package.findAll", query = "select p from Package p"),
        @NamedQuery(name = "Package.findByReceiver", query = "select p from Package p where p.receiver.Id = :receiverId"),
        @NamedQuery(name = "Package.findBySender", query = "select p from Package p where p.sender.Id = :senderId"),
        @NamedQuery(name = "Package.findByCourier", query = "select  p from Package p join p.couriers c where c.id = :courierId")
})
public class Package implements Serializable {

    public Package(float weight, Customer sender, Customer receiver, String shipAddress){
        this.setWeight(weight);
        this.setSender(sender);
        this.setReceiver(receiver);
        this.setShipAddress(shipAddress);
    }

    public Package(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @DecimalMin("0.001")
    @EqualsAndHashCode.Exclude
    @Column(name = "PACKAGE_WEIGHT")
    private float weight;

    @ManyToOne
    @NotNull
    private Customer sender;

    @ManyToOne
    @NotNull
    private Customer receiver;

    @Size(max = 50)
    @NotEmpty
    @Column(name = "PACKAGE_SHIP_ADDRESS")
    private String shipAddress;

    @ManyToMany(mappedBy = "deliveries")
    @EqualsAndHashCode.Exclude
    private List<Employee> couriers = new ArrayList<>();

    @PreRemove
    private void removePackageFromDeliveries(){
        for(Employee courier : couriers){
            courier.removeDelivery(this);
        }
    }
}
