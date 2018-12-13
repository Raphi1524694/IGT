package IGT.Flight;

import IGT.Customer.Customer;
import IGT.IClassID;
import org.codehaus.jettison.json.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "Bookings")
public class Booking implements IClassID {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "booking")
    private Long id;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


    @OneToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    public Booking() {
    }

    public Booking(Customer customer, Flight flight) {
        this.customer = customer;
        this.flight = flight;
    }

    @Override
    public String getClassId() {
        return "Booking";
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("booking_id", this.id);
            object.put("customer_id", this.customer.getId());
            object.put("flight_id", this.flight.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
