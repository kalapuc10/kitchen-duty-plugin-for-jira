package by.kalaputs.kitchen.duty.ao;

import net.java.ao.Entity;

public interface UserToWeek extends Entity {
    User getUser();

    void setUser(User user);

    Week getWeek();

    void setWeek(Week week);
}
