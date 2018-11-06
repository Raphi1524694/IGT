import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Customers")
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    @OneToMany(mappedBy="belongsToCustomer", cascade = CascadeType.ALL)
    private List<Phone> phoneList;

    public Customer(String name) {
        setName(name);
        phoneList = new ArrayList<Phone>();
    }

    public Customer() {   }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Phone> getPhones(){
        return phoneList;
    }

    public void addPhone(Phone p){
        if (!phoneList.contains(p)) {
            phoneList.add(p);
            if (p.belongsToCustomer != null){
                p.belongsToCustomer.phoneList.remove(p);
            }
            p.setBelongsToCustomer(this);

        }
    }
}


@Entity
@Table(name = "Customer_Phones")
class Phone {
    @Id
    @GeneratedValue
    private Long id;

    private String phoneNumber;

    @ManyToOne
    public Customer belongsToCustomer;

    Phone(String number){
        phoneNumber = number;
    }

    public void setBelongsToCustomer(Customer c){
        belongsToCustomer = c;
    }
}