package by.kalaputs.kitchen.duty.rest;

import com.atlassian.plugins.rest.common.security.AnonymousAllowed;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/message")
public class UserSearchResource {
    @GET
    @AnonymousAllowed
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getMessage() {
        return Response.ok(new UserSearchResourceModel("Hello World")).build();
    }
}
