package IGT.Customer;

import IGT.IClassID;
import org.codehaus.jettison.json.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "Customer_Phones")
public class Phone implements IClassID {
    @Id
    @GeneratedValue
    private Long id;

    private String phoneNumber;

    @ManyToOne
    public Customer belongsToCustomer;

    public Phone(String number) {
        phoneNumber = number;
    }

    public Phone() {
    }

    public void setBelongsToCustomer(Customer c) {
        belongsToCustomer = c;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String getClassId() {
        return "Phone";
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", getId());
        } catch (Exception e) {
            System.out.println("cannot convert customer " + this.getPhoneNumber() + " to json");
            e.printStackTrace();
        }
        return json;
    }
}