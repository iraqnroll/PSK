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
@Getter
@Setter
@Table(name = "CUSTOMER")
@EqualsAndHashCode
@NamedQueries({
        @NamedQuery(name = "Customer.findAll"
                , query = "select c from Customer c"),
})
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @NotEmpty
    @Size(max = 20)
    @Column(name = "CUSTOMER_NAME")
    private String name;

    @NotEmpty
    @Size(max = 30)
    @Column(name = "CUSTOMER_SURNAME")
    private String surname;

    @OneToMany(mappedBy = "receiver")
    @EqualsAndHashCode.Exclude
    private List<Package> packagesToReceive = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    @EqualsAndHashCode.Exclude
    private List<Package> packagesToSend = new ArrayList<>();

    @Version
    @Column(name = "OPT_LOCK_VERSION")
    private Integer version;

    @Override
    public String toString() {
        return this.name + " " + this.surname + " " + this.version;
    }
}
