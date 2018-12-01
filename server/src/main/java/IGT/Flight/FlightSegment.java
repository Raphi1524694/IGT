package IGT.Flight;

import IGT.IClassID;
import org.codehaus.jettison.json.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "FlightSegments")
public class FlightSegment implements IClassID {

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

    public FlightSegment(Flight f, Airport start, Airport goal) {
        this.setBelongsToFlight(f);
        this.start = start;
        this.goal = goal;
    }

    public void setBelongsToFlight(Flight f){
        belongsToFlight = f;
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
            System.out.println("cannot convert customer " + this.getId() + " to json");
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public String getClassId() {
        return "FlightSegment";
    }
}