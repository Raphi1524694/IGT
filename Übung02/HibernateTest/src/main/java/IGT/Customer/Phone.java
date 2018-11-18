package IGT.Customer;

import javax.persistence.*;

@Entity
@Table(name = "Customer_Phones")
public class Phone {
    @Id
    @GeneratedValue
    private Long id;

    private String phoneNumber;

    @ManyToOne
    public Customer belongsToCustomer;

    public Phone(String number) {
        phoneNumber = number;
    }

    public Phone(){}

    public void setBelongsToCustomer(Customer c) {
        belongsToCustomer = c;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}