package IGT;


import org.codehaus.jettison.json.JSONObject;

public interface IClassID {
    String getClassId();

    Long getId();
    JSONObject toJSON();
}
