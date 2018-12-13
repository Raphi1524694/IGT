package IGT;


import org.codehaus.jettison.json.JSONObject;

import java.io.Serializable;

public interface IClassID {
    String getClassId();

    Serializable getId();
    JSONObject toJSON();
}
