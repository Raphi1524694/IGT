package IGT.Flight;

import IGT.IClassID;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.persistence.*;


@Entity
@Table(name = "Airports")
public class Airport implements IClassID {
    @Id
    @GeneratedValue
    private Long id;

    public String name;

    public String shortName;

    public Airport() {
    }

    public Airport(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    @Override
    public String getClassId() {
        return "Airport";
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public JSONObject toJSON() {
        JSONObject flight = new JSONObject();
        try {
            flight.put("airportId", this.getId());
            flight.put("name", name);
            flight.put("short", shortName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return flight;
    }
}