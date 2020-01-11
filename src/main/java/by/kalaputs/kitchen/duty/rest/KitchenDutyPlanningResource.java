package by.kalaputs.kitchen.duty.rest;

import by.kalaputs.kitchen.duty.ao.User;
import by.kalaputs.kitchen.duty.ao.Week;
import by.kalaputs.kitchen.duty.rest.model.KitchenDutyPlanningResourceUserModel;
import by.kalaputs.kitchen.duty.rest.model.KitchenDutyPlanningResourceWeekModel;
import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.crowd.integration.rest.entity.PropertyEntity;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import net.java.ao.Query;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Named
@Path("/planning")
public class KitchenDutyPlanningResource {
    private ActiveObjects activeObjects;

    @Inject
    public KitchenDutyPlanningResource(ActiveObjects activeObjects) {
        this.activeObjects = activeObjects;
    }

    @GET
    @Path("/week/{weekNumber}/users")
    @Produces({MediaType.APPLICATION_JSON})
    @AnonymousAllowed
    public Response getUsersForWeek(@PathParam("weekNumber") final Integer weekNumber) {
        Week[] weeks = activeObjects.executeInTransaction(() -> activeObjects.find(Week.class, Query.select().where("WEEK = ?", weekNumber)));
        List<KitchenDutyPlanningResourceUserModel> users = new ArrayList<>();
        if (weeks != null && weeks.length > 0) {
            for (User user : weeks[0].getUsers()) {
                users.add(
                    new KitchenDutyPlanningResourceUserModel(
                        user.getID(),
                        user.getName()
                    )
                );
            }
        }
        return Response.ok(users).build();
    }

    @GET
    @Path("/user/{username}/weeks")
    @Produces({MediaType.APPLICATION_JSON})
    @AnonymousAllowed
    public Response getWeeksForUser(@PathParam("username") final String username) {
        User[] users = activeObjects.executeInTransaction(() -> activeObjects.find(User.class, Query.select().where("NAME = ?", username)));
        List<KitchenDutyPlanningResourceWeekModel> weeks = new ArrayList<>();
        if (users != null && users.length > 0) {
            for (Week week : users[0].getWeeks()) {
                weeks.add(new KitchenDutyPlanningResourceWeekModel(week.getID(), week.getWeek()));
            }
        }
        return Response.ok(weeks).build();
    }

    @GET
    @Path("/health")
    @Produces({MediaType.APPLICATION_JSON})
    @AnonymousAllowed
    public Response health() {
        return Response.ok().entity(new PropertyEntity("OK", "TEST")).build();
    }
}
