package IGT.Customer;

import IGT.Flight.Flight;
import IGT.Hibernate;
import IGT.IClassID;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;


@Entity
@Table(name = "Customers")
public class Customer implements IClassID {
    private static String table = "Customer";

    @Id
    @GeneratedValue
    @Column(name = "customer_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "belongsToCustomer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Phone> phoneList = new ArrayList<Phone>();

    @ManyToMany(mappedBy = "customers")
    public Set<Flight> flights = new HashSet<Flight>();

    public Customer(String name) {
        setName(name);
    }

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Phone> getPhones() {
        return phoneList;
    }

    public void removeAllPhones() {
        phoneList.forEach(phone -> phone.setBelongsToCustomer(null));
        //phoneList.forEach(phone -> Hibernate.getInstance().delete(phone));
        phoneList.clear();
    }

    public void addPhone(Phone p) {
        if (!phoneList.contains(p)) {
            phoneList.add(p);
            if (p.belongsToCustomer != null) {
                p.belongsToCustomer.phoneList.remove(p);
            }
            p.setBelongsToCustomer(this);
        }
    }

    public JSONObject toJSON() {
        JSONObject customerJson = new JSONObject();
        try {
            customerJson.put("customerId", id);
            customerJson.put("name", getName());
            JSONArray phones = new JSONArray();
            phoneList.forEach(phone -> phones.put(phone.getPhoneNumber()));
            customerJson.put("phones", phones);
        } catch (Exception e) {
            System.out.println("cannot convert customer" + getName() + " to json");
            e.printStackTrace();
        }
        return customerJson;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == this.getClass()) {
            if (((Customer) obj).getId() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getClassId() {
        return "Customer";
    }
}