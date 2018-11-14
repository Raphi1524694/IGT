package IGT.Flight;

import javax.persistence.*;

@Entity
@Table(name = "FlightSegments")
public class FlightSegment{

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