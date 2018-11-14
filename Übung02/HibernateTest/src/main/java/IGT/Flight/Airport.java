package IGT.Flight;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Airports")
public class Airport{
    @Id
    public String name;

    public Airport(){}

    public Airport(String name){
        this.name = name;
    }
}