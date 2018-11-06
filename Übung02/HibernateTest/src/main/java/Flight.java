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


@Entity
@Table(name = "FlightSegments")
class FlightSegment{

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    public Flight belongsToFlight;


    @OneToOne(cascade = CascadeType.ALL)
    private Airport start;


    @OneToOne(cascade = CascadeType.ALL)
    private Airport goal;

    public FlightSegment(){}

    public FlightSegment(Airport start, Airport goal){
        this.start = start;
        this.goal = goal;
    }

    public void setBelongsToFlight(Flight f){
        belongsToFlight = f;
    }
}

@Entity
@Table(name = "Airports")
class Airport{
    @Id
    public String name;

    public Airport(){}

    public Airport(String name){
        this.name = name;
    }
}