package by.kalaputs.kitchen.duty.ao;

import net.java.ao.Entity;
import net.java.ao.ManyToMany;
import net.java.ao.Preload;
import net.java.ao.schema.NotNull;
import net.java.ao.schema.Unique;

import java.util.List;

@Preload
public interface User extends Entity {
    @NotNull
    @Unique
    String getName();

    void setName(String week);

    @ManyToMany(value = UserToWeek.class)
    List<Week> getWeeks();
}
