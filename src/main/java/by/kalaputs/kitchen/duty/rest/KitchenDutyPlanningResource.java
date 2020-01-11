package by.kalaputs.kitchen.duty.rest;

import by.kalaputs.kitchen.duty.ao.User;
import by.kalaputs.kitchen.duty.ao.UserToWeek;
import by.kalaputs.kitchen.duty.ao.Week;
import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.crowd.integration.rest.entity.PropertyEntity;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import net.java.ao.DBParam;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Named
@Path("/planning")
public class KitchenDutyPlanningResource {
    private ActiveObjects activeObjects;

    @Inject
    public KitchenDutyPlanningResource(ActiveObjects activeObjects) {
        this.activeObjects = activeObjects;
    }

    @GET
    @Path("/persistTest")
    @Produces({MediaType.APPLICATION_JSON})
    @AnonymousAllowed
    public Response persistTest() {
        activeObjects.executeInTransaction(() -> {
            final Week testWeek = activeObjects.create(
                Week.class,
                new DBParam("WEEK", 42));
            testWeek.save();

            final User user = activeObjects.create(
                User.class,
                new DBParam("NAME", "kalaputs"));
            user.save();

            final UserToWeek relationship = activeObjects.create(UserToWeek.class);
            relationship.setUser(user);
            relationship.setWeek(testWeek);

            relationship.save();

            return testWeek;
        });
        return Response.ok().entity(new PropertyEntity("OK", "TEST")).build();
    }

    @GET
    @Path("/health")
    @Produces({MediaType.APPLICATION_JSON})
    @AnonymousAllowed
    public Response health() {
        return Response.ok().entity(new PropertyEntity("OK", "TEST")).build();
    }
}
