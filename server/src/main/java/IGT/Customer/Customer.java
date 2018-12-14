package IGT.Customer;

import IGT.Flight.Booking;
import IGT.Flight.Flight;
import IGT.IClassID;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NaturalId;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.*;


@Entity
@Indexed
@Table(name = "Customers")
public class Customer implements IClassID {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @NaturalId
    private String name;

    @Column
    private String address;

    @Column
    private int flownMiles = 0;

    @Column
    private EStatus status;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "belongsToCustomer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Phone> phoneList = new ArrayList<>();

    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Booking> bookings = new ArrayList<>();

    public Customer(String name, String address, int flownMiles) {
        setName(name);
        setAddress(address);
        setFlownMiles(flownMiles);
    }

    private Customer() { }

    public List getBookings() {
        return this.bookings;
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

    public void bookFlight(Flight flight) {
        Booking booking = new Booking(this, flight);
        this.bookings.add(booking);
        flight.getBookings().add(booking);
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
            for (Booking booking : this.bookings) {
                flights.put(booking.getFlight().toJSON());
            }
            customerJson.put("flights", flights);
            customerJson.put("phones", phones);
        } catch (Exception e) {
            System.out.println("cannot convert customer " + this.getName() + " to json");
            e.printStackTrace();
        }
        return customerJson;
    }

    @Override
    public String getClassId() {
        return "Customer";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(this.name, customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
}