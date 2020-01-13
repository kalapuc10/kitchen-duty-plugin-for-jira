package by.kalaputs.kitchen.duty.rest;

import by.kalaputs.kitchen.duty.ao.User;
import by.kalaputs.kitchen.duty.ao.Week;
import by.kalaputs.kitchen.duty.ao.helper.KitchenDutyActiveObjectHelper;
import by.kalaputs.kitchen.duty.rest.model.KitchenDutyOverviewPageMonthDutyModel;
import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.atlassian.sal.api.user.UserManager;

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
import java.util.stream.Collectors;

@Named
@Path("/overview_page")
public class KitchenDutyOverviewPageResource extends BaseResource {
    @Inject
    public KitchenDutyOverviewPageResource(ActiveObjects activeObjects, UserManager userManager) {
        this.activeObjects = activeObjects;
        this.userManager = userManager;
    }

    @GET
    @Path("/year/{year}/month/{month}")
    @Produces({MediaType.APPLICATION_JSON})
    @AnonymousAllowed
    public Response getUsersForWeek(@PathParam("year") final Integer year, @PathParam("month") final Integer month) {
        List<KitchenDutyOverviewPageMonthDutyModel> responseList = new ArrayList<>();
        List<Integer> weekNumbersInMonth = getWeeksOfMonth(year, month);
        for (Integer weekNumber : weekNumbersInMonth) {
            Week week = KitchenDutyActiveObjectHelper
                .getWeekByWeekNumberInTransaction(activeObjects, weekNumber);
            List<User> usersForWeek = KitchenDutyActiveObjectHelper
                .getUsersAssignedToWeekInTransaction(activeObjects, week);
            List<String> usernames = usersForWeek
                .stream()
                .map(User::getName)
                .collect(Collectors.toList());
            responseList.add(new KitchenDutyOverviewPageMonthDutyModel(
                weekNumber,
                getFirstDayOfWeekOfYear(year, weekNumber).toString(),
                getLastDayOfWeekOfYear(year, weekNumber).toString(),
                usernames)
            );
        }

        return Response.ok(responseList).build();
    }
}
