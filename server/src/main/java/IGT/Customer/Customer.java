package IGT.Customer;

import IGT.Flight.Flight;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    private Long id;

    private String name;

    private String address;

    private int flownMiles = 0;

    private EStatus status;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "belongsToCustomer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Phone> phoneList = new ArrayList<Phone>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinTable(
            name = "FlightBookings",
            joinColumns = {@JoinColumn(name = "customer_id")},
            inverseJoinColumns = {@JoinColumn(name = "flight_id")}
    )
    public Set<Flight> flights = new HashSet<Flight>();

    public Customer(String name, String address, int flownMiles) {
        setName(name);
        setAddress(address);
        setFlownMiles(flownMiles);
    }

    public Customer() {
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addFlownMiles(int miles) {
        this.setFlownMiles(flownMiles + miles);
    }

    public void setFlownMiles(int miles) {
        flownMiles = miles;
        if (flownMiles >= EStatus.WHITE_GOLD.getMiles()) {
            status = EStatus.WHITE_GOLD;
            return;
        }
        if (flownMiles >= EStatus.SPECIAL_PLATINUM.getMiles()) {
            status = EStatus.SPECIAL_PLATINUM;
            return;
        }
        if (flownMiles >= EStatus.PLATINUM.getMiles()) {
            status = EStatus.PLATINUM;
            return;
        }
        if (flownMiles >= EStatus.GOLD.getMiles()) {
            status = EStatus.GOLD;
            return;
        }
        if (flownMiles >= EStatus.SILVER.getMiles()) {
            status = EStatus.SILVER;
            return;
        }
        if (flownMiles >= EStatus.BRONZE.getMiles()) {
            status = EStatus.BRONZE;
            return;
        }
        status = EStatus.NONE;
    }

    public void addFlight(Flight flight) {
        this.flights.add(flight);
    }


    public List<Phone> getPhones() {
        return phoneList;
    }

    public void removeAllPhones() {
        for (Phone phone : phoneList) {
            phone.setBelongsToCustomer(null);
        }
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
            customerJson.put("address", getAddress());
            customerJson.put("flownMiles", flownMiles);
            customerJson.put("status", status.name());
            JSONArray phones = new JSONArray();
            for (Phone phone : phoneList) {
                phones.put(phone.getPhoneNumber());
            }
            customerJson.put("phones", phones);
        } catch (Exception e) {
            System.out.println("cannot convert customer " + this.getName() + " to json");
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