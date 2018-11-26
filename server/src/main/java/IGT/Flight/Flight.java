package IGT.Flight;

import IGT.Customer.Customer;
import IGT.IClassID;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Flights")
public class Flight implements IClassID {

    @Id
    @GeneratedValue
    @Column(name = "flight_id")
    private Long id;

    public Date startTime;
    public Date arrivalTime;

    public int seatsEconomyClass;
    public int seatsFistClass;

    public PlaneType plane;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "FlightBookings",
            joinColumns = {@JoinColumn(name = "flight_id")},
            inverseJoinColumns = {@JoinColumn(name = "customer_id")}
    )
    public Set<Customer> customers = new HashSet<Customer>();

    public Flight() {
    }

    @OneToMany(mappedBy = "belongsToFlight", cascade = CascadeType.ALL)
    private List<FlightSegment> flightSegmentList = new ArrayList<FlightSegment>();

    public void addFlightSegment(FlightSegment p) {
        if (!flightSegmentList.contains(p)) {
            flightSegmentList.add(p);
            if (p.belongsToFlight != null) {
                p.belongsToFlight.flightSegmentList.remove(p);
            }
            p.setBelongsToFlight(this);
        }
    }


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getClassId() {
        return "Flight";
    }
}