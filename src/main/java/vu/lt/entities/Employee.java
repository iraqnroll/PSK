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
@Table(name = "EMPLOYEE")
@Getter
@Setter
@EqualsAndHashCode
@NamedQueries({
        @NamedQuery(name = "Employee.findAll", query = "select e from Employee e")
})
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 20)
    @NotEmpty
    @Column(name = "EMPLOYEE_NAME")
    private String name;

    @Size(max = 30)
    @NotEmpty
    @Column(name = "EMPLOYEE_SURNAME")
    private String surname;

    @ManyToMany
    @EqualsAndHashCode.Exclude
    private List<Package> deliveries = new ArrayList<>();

    public void removeDelivery(Package pkg){
        this.deliveries.removeIf(rec -> rec.getId().equals(pkg.getId()));
    }
}
