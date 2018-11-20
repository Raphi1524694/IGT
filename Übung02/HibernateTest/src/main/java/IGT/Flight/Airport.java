package IGT.Flight;

import IGT.IClassID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Airports")
public class Airport implements IClassID {
    @Id
    public String name;

    public Airport(){}

    public Airport(String name){
        this.name = name;
    }

    @Override
    public String getClassId() {
        return "Airport";
    }

    @Override
    public Object getId() {
        return name;
    }
}