package by.kalaputs.kitchen.duty.rest;

import by.kalaputs.kitchen.duty.ao.User;
import by.kalaputs.kitchen.duty.ao.UserToWeek;
import by.kalaputs.kitchen.duty.ao.Week;
import by.kalaputs.kitchen.duty.ao.helper.KitchenDutyActiveObjectHelper;
import by.kalaputs.kitchen.duty.rest.model.KitchenDutyPlanningResourceUserModel;
import by.kalaputs.kitchen.duty.rest.model.KitchenDutyPlanningResourceWeekModel;
import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.crowd.integration.rest.entity.PropertyEntity;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import net.java.ao.DBParam;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
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
        Week week = KitchenDutyActiveObjectHelper.findUniqueWeek(activeObjects, weekNumber);
        List<KitchenDutyPlanningResourceUserModel> users = new ArrayList<>();
        if (week != null)
            for (User user : week.getUsers())
                users.add(new KitchenDutyPlanningResourceUserModel(user.getID(), user.getName()));

        return Response.ok(users).build();
    }

    @PUT
    @Path("/week/{weekNumber}/users")
    @Produces({MediaType.APPLICATION_JSON})
    @AnonymousAllowed
    public Response addUserToWeek(@PathParam("weekNumber") final Integer weekNumber, final KitchenDutyPlanningResourceUserModel userParam) {
        activeObjects.executeInTransaction(() -> {
            Week week = KitchenDutyActiveObjectHelper.findUniqueWeek(activeObjects, weekNumber);
            if (week == null) {
                week = activeObjects.create(Week.class, new DBParam("WEEK", weekNumber));
                week.save();
            }

            User user = KitchenDutyActiveObjectHelper.findUniqueUser(activeObjects, userParam.getUsername());
            if (user == null) {
                user = activeObjects.create(User.class, new DBParam("NAME", userParam.getUsername()));
                user.save();
            }

            UserToWeek relationship = KitchenDutyActiveObjectHelper.findRelationship(activeObjects, user, week);
            if (relationship != null) {
                // relation already exists
                return null;
            }
            relationship = activeObjects.create(UserToWeek.class);
            relationship.setUser(user);
            relationship.setWeek(week);
            relationship.save();

            return null;
        });
        return Response.ok().entity(new PropertyEntity("OK", "CREATED")).build();
    }

    @DELETE
    @Path("/week/{weekNumber}/users")
    @Produces({MediaType.APPLICATION_JSON})
    @AnonymousAllowed
    public Response deleteUserFomWeek(@PathParam("weekNumber") final Integer weekNumber, final KitchenDutyPlanningResourceUserModel userParam) {
        activeObjects.executeInTransaction(() -> {
            Week week = KitchenDutyActiveObjectHelper.findUniqueWeek(activeObjects, weekNumber);
            if (week == null)
                return null;

            User user = KitchenDutyActiveObjectHelper.findUniqueUser(activeObjects, userParam.getUsername());
            if (user == null)
                return null;

            UserToWeek relationship = KitchenDutyActiveObjectHelper.findRelationship(activeObjects, user, week);
            if (relationship != null)
                activeObjects.delete(relationship);

            return null;
        });
        return Response.ok().entity(new PropertyEntity("OK", "DELETED")).build();
    }

    @GET
    @Path("/user/{username}/weeks")
    @Produces({MediaType.APPLICATION_JSON})
    @AnonymousAllowed
    public Response getWeeksForUser(@PathParam("username") final String username) {
        User user = KitchenDutyActiveObjectHelper.findUniqueUser(activeObjects, username);
        List<KitchenDutyPlanningResourceWeekModel> weeks = new ArrayList<>();
        if (user != null)
            for (Week week : user.getWeeks())
                weeks.add(new KitchenDutyPlanningResourceWeekModel(week.getID(), week.getWeek()));

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
