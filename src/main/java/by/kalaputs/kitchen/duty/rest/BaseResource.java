package by.kalaputs.kitchen.duty.rest;

import by.kalaputs.kitchen.duty.rest.model.RestError;
import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.sal.api.user.UserProfile;

import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BaseResource {
    protected UserManager userManager;
    protected ActiveObjects activeObjects;

    public Boolean isUserNotAdmin() {
        UserProfile user = userManager.getRemoteUser();
        return (user == null || !userManager.isAdmin(user.getUserKey()));
    }

    protected Response getForbiddenErrorResponse() {
        return Response.serverError()
            .entity(new RestError(
                RestError.errorText403,
                403001,
                Response.Status.FORBIDDEN.getStatusCode()))
            .status(Response.Status.FORBIDDEN)
            .build();
    }

    public static List<Integer> getWeeksOfMonth(Integer year, Integer month) {
        List<Integer> weeks = new ArrayList<>();
        weeks.add(getWeekOfMonth(year, month, 1));
        weeks.add(getWeekOfMonth(year, month, 2));
        weeks.add(getWeekOfMonth(year, month, 3));
        weeks.add(getWeekOfMonth(year, month, 4));
        weeks.add(getWeekOfMonth(year, month, 5));
        return weeks;
    }

    protected static Integer getWeekOfMonth(Integer year, Integer month, Integer weekInMonth) {
        // https://docs.oracle.com/javase/8/docs/api/java/time/temporal/WeekFields.html
        // For locale en_US weeks start on sunday
        WeekFields weekFields = WeekFields.of(Locale.forLanguageTag("en_US"));
        LocalDate origin = LocalDate.of(1970, 1, 1);
        LocalDate reference = origin
            .with(weekFields.weekBasedYear(), year)
            .with(ChronoField.YEAR, year)
            .with(ChronoField.MONTH_OF_YEAR, month)
            .with(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH, 1)
            .with(ChronoField.ALIGNED_WEEK_OF_MONTH, weekInMonth);
        return reference.get(weekFields.weekOfYear());
    }

    public static LocalDate getFirstDayOfWeekOfYear(Integer year, Integer week) {
        // https://docs.oracle.com/javase/8/docs/api/java/time/temporal/WeekFields.html
        WeekFields weekFields = WeekFields.of(Locale.forLanguageTag("en_US"));
        return LocalDate.now()
            .with(weekFields.weekBasedYear(), year)
            .with(ChronoField.ALIGNED_WEEK_OF_YEAR, week)
            .with(ChronoField.DAY_OF_WEEK, 1).minusDays(1);
    }

    public static LocalDate getLastDayOfWeekOfYear(Integer year, Integer week) {
        return getFirstDayOfWeekOfYear(year, week).plusDays(6);
    }
}
