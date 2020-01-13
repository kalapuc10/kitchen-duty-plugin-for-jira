package by.kalaputs.kitchen.duty.ao.helper;

import by.kalaputs.kitchen.duty.ao.User;
import by.kalaputs.kitchen.duty.ao.UserToWeek;
import by.kalaputs.kitchen.duty.ao.Week;
import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.sal.api.transaction.TransactionCallback;
import net.java.ao.Query;

import java.util.ArrayList;
import java.util.List;

public class KitchenDutyActiveObjectHelper {
    public static Week findUniqueWeek(ActiveObjects activeObjects, Integer weekNumber) {
        Week[] weekRes = activeObjects.find(Week.class, Query.select().where("WEEK = ?", weekNumber));
        if ((weekRes != null && weekRes.length > 0)) {
            return weekRes[0];
        }
        return null;
    }

    public static User findUniqueUser(ActiveObjects activeObjects, String username) {
        User[] userRes = activeObjects.find(User.class, Query.select().where("NAME = ?", username));
        if ((userRes != null && userRes.length > 0)) {
            return userRes[0];
        }
        return null;
    }

    public static UserToWeek findRelationship(ActiveObjects activeObjects, User user, Week week) {
        UserToWeek[] relationships = activeObjects.find(UserToWeek.class, Query.select().where("USER_ID = ? AND WEEK_ID = ?", user.getID(), week.getID()));
        if ((relationships != null && relationships.length > 0)) {
            return relationships[0];
        }
        return null;
    }

    public static UserToWeek[] findAllRelationships(ActiveObjects activeObjects, Week week) {
        return activeObjects.find(UserToWeek.class, Query.select().where("WEEK_ID = ?", week.getID()));
    }

    public static Week getWeekByWeekNumberInTransaction(ActiveObjects activeObjects, Integer weekNumber) {
        return activeObjects.executeInTransaction(() -> findUniqueWeek(activeObjects, weekNumber));
    }

    public static List<User> getUsersAssignedToWeekInTransaction(ActiveObjects activeObjects, Week week) {
        List<User> users = new ArrayList<>();
        if (week != null) {
            UserToWeek[] relationships = activeObjects.executeInTransaction(() -> findAllRelationships(activeObjects, week));
            if (relationships != null)
                for (UserToWeek userToWeek : relationships)
                    users.add(userToWeek.getUser());
        }
        return users;
    }
}
