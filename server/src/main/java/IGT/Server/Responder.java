package IGT.Server;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

public class Responder {
    static Response build(Response.Status stat, String entity) {
        return cors(Response.status(stat).entity(entity)).build();
    }

    private static ResponseBuilder cors(ResponseBuilder resp) {
        return resp.header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
    }

    static Response badRequest() {
        System.out.println("bad request");
        return Responder.build(Response.Status.BAD_REQUEST, "Bad Request");
    }

    static Response badRequest(String message) {
        System.out.println("bad request");
        return Responder.build(Response.Status.BAD_REQUEST, message);
    }


    public static Response created(Object responseForUser) {
        System.out.println("created");
        return build(Response.Status.CREATED, responseForUser.toString());
    }


    public static Response ok(Object responseForUser) {
        System.out.println("ok");
        return build(Response.Status.OK, responseForUser.toString());
    }

    public static Response preFlight() {
        return Response.ok("").header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600").build();
    }

    public static Response exception(Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
        return Responder.build(Response.Status.BAD_REQUEST, e.getMessage());
    }
}
