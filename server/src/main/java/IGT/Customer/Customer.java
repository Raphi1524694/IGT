package IGT.Customer;

import IGT.Flight.Flight;
import IGT.IClassID;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "Customers")
public class Customer implements IClassID {
    private static String table = "Customer";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    private Long id;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private int flownMiles = 0;

    @Column
    private EStatus status;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "belongsToCustomer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Phone> phoneList = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL )
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "Bookings",
            joinColumns = {@JoinColumn(name = "customer_id")},
            inverseJoinColumns = {@JoinColumn(name = "flight_id")}
    )
    public List<Flight> flights = new ArrayList<>();

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

    public void bookFlight(Flight flight) throws Exception {
        for (Flight f : this.flights) {
            if (f.getId().equals(flight.getId())) {
                throw new Exception("Flight with id " + flight.getId() + " already exists for Customer " + this.getId());
            }
        }
        this.flights.add(flight);
        flight.addCustomer(this);

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
            JSONArray flights = new JSONArray();
            for (Flight flight : this.flights) {
                flights.put(flight.toJSON());
            }
            customerJson.put("phones", phones);
            customerJson.put("flights", flights);
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