package vu.lt.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @EqualsAndHashCode.Exclude
    @Column(name = "PACKAGE_WEIGHT")
    private float weight;

    @ManyToOne
    @NotEmpty
    private Customer sender;

    @ManyToOne
    @NotEmpty
    private Customer receiver;

    @Size(max = 50)
    @NotEmpty
    @Column(name = "PACKAGE_SHIP_ADDRESS")
    private String shipAddress;

    @ManyToMany(mappedBy = "deliveries")
    @EqualsAndHashCode.Exclude
    private List<Employee> couriers = new ArrayList<>();
}
