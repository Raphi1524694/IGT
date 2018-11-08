package Flight;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Flights")
public class Flight {

    @Id
    @GeneratedValue
    private Long id;

    public Date startTime;
    public Date arrivalTime;

    public int seatsEconomyClass;
    public int seatsFistClass;

    public PlaneType plane;

    public Flight(){}

    @OneToMany(mappedBy="belongsToFlight", cascade = CascadeType.ALL)
    private List<FlightSegment> flightSegmentList = new ArrayList<FlightSegment>();

    public void addFlightSegment(FlightSegment p){
        if (!flightSegmentList.contains(p)) {
            flightSegmentList.add(p);
            if (p.belongsToFlight != null){
                p.belongsToFlight.flightSegmentList.remove(p);
            }
            p.setBelongsToFlight(this);
        }
    }
}