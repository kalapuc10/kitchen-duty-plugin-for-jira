package by.kalaputs.kitchen.duty.rest.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "duty")
@XmlAccessorType(XmlAccessType.FIELD)
public class KitchenDutyOverviewPageMonthDutyModel {
    @XmlElement
    private Integer week;

    @XmlElement
    private String start; /* Date String - First day of week (Sunday) */

    @XmlElement
    private String end; /* Date String - Last day of week (Monday) */

    @XmlElement
    private List<String> users;

    public KitchenDutyOverviewPageMonthDutyModel(Integer week, String start, String end, List<String> users) {
        this.start = start;
        this.end = end;
        this.week = week;
        this.users = users;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
