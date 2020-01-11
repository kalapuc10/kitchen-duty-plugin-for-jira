package by.kalaputs.kitchen.duty.ao;

import net.java.ao.Entity;
import net.java.ao.ManyToMany;
import net.java.ao.Preload;
import net.java.ao.schema.NotNull;
import net.java.ao.schema.Unique;

import java.util.List;

@Preload
public interface Week extends Entity {
    @NotNull
    @Unique
    Integer getWeek();

    void setWeek(Integer week);

    @ManyToMany(value = UserToWeek.class)
    List<User> getUsers();
}
