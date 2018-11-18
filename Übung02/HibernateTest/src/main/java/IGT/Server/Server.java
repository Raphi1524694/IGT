package IGT.Server;

import IGT.Hibernate;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/message")
public class Server{
    @GET
    public String getMsg()
    {
        Hibernate.getInstance().insert();
        System.out.println("called");
        return "Hello World !! - Jersey 2";
    }
}
