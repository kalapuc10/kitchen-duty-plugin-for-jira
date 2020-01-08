package by.kalaputs.kitchen.duty.rest;

import com.atlassian.jira.bc.user.search.UserSearchService;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
public class UserSearchResource {
    private UserSearchService userSearchService;

    @Inject
    public UserSearchResource(UserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }

    public UserSearchResource() {
    }

    @GET
    @AnonymousAllowed
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getMessage() {
        return Response.ok(new UserSearchResourceModel("Hello World")).build();
    }
}
