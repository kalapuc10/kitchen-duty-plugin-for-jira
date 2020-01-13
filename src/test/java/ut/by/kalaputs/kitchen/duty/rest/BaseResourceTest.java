package ut.by.kalaputs.kitchen.duty.rest;

import by.kalaputs.kitchen.duty.rest.BaseResource;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class BaseResourceTest {
    @Test
    public void testGetWeeksOfMonth__weekStartsInPreviousMonth() {
        // Sunday to Saturday:
        // Week 31  July   29, 2018 => August  4, 2018
        // Week 32  August  5, 2018 => August 11, 2018
        // Week 33  August 12, 2018 => August 18, 2018
        // Week 34  August 19, 2018 => August 25, 2018
        // Week 35  August 26, 2018 => Sept,   1, 2018
        {
            List<Integer> weeks = BaseResource.getWeeksOfMonth(2018, 8);
            assertThat(weeks, is(Arrays.asList(31, 32, 33, 34, 35)));
        }

        // Sunday to Saturday:
        // Week 5  January  28, 2018 => February  3, 2018
        // Week 6  February  4, 2018 => February 10, 2018
        // Week 7  February 11, 2018 => February 17, 2018
        // Week 8  February 18, 2018 => February 24, 2018
        // Week 9  February 25, 2018 => March     3, 2018
        {
            List<Integer> weeks = BaseResource.getWeeksOfMonth(2018, 2);
            assertThat(weeks, is(Arrays.asList(5, 6, 7, 8, 9)));
        }

        // Sunday to Saturday:
        // Week 18  April 29, 2018 => May   5, 2018
        // Week 19  May    6, 2018 => May  12, 2018
        // Week 20  May   13, 2018 => May  19, 2018
        // Week 21  May   20, 2018 => May  26, 2018
        // Week 22  May   27, 2018 => June  2, 2018
        {
            List<Integer> weeks = BaseResource.getWeeksOfMonth(2018, 5);
            assertThat(weeks, is(Arrays.asList(18, 19, 20, 21, 22)));
        }
    }

    @Test
    public void testGetWeeksOfMonth__weekStartsExactlyInMonth() {
        // Sunday to Saturday:
        // Week 19  May  1, 2016 => May  7, 2016
        // Week 20  May  8, 2016 => May 14, 2016
        // Week 21  May 15, 2016 => May 21, 2016
        // Week 22  May 22, 2016 => May 28, 2016
        // Week 23  May 29, 2016 => June 4, 2016
        {
            List<Integer> weeks = BaseResource.getWeeksOfMonth(2016, 5);
            assertThat(weeks, is(Arrays.asList(19, 20, 21, 22, 23)));
        }

        // Sunday to Saturday:
        // Week 27  July  1, 2018 => July  7, 2018
        // Week 28  July  8, 2018 => July 14, 2018
        // Week 29  July 15, 2018 => July 21, 2018
        // Week 30  July 22, 2018 => July 28, 2018
        // Week 31  July 29, 2018 => Aug.  4, 2018
        {
            List<Integer> weeks = BaseResource.getWeeksOfMonth(2018, 7);
            assertThat(weeks, is(Arrays.asList(27, 28, 29, 30, 31)));
        }
    }

    @Test
    public void testGetFirstDayOfWeekOfMonth() {
        LocalDate date = BaseResource.getFirstDayOfWeekOfYear(2020, 1);
        assertEquals("2020-01-05", date.toString());

        LocalDate date1 = BaseResource.getFirstDayOfWeekOfYear(2018, 32);
        assertEquals("2018-08-05", date1.toString());

        LocalDate date2 = BaseResource.getFirstDayOfWeekOfYear(2018, 31);
        assertEquals("2018-07-29", date2.toString());

        LocalDate date3 = BaseResource.getFirstDayOfWeekOfYear(2018, 27);
        assertEquals("2018-07-01", date3.toString());
    }

    @Test
    public void testGetLastDayOfWeekOfMonth() {
        LocalDate date = BaseResource.getLastDayOfWeekOfYear(2018, 32);
        assertEquals("2018-08-11", date.toString());

        LocalDate date2 = BaseResource.getLastDayOfWeekOfYear(2018, 31);
        assertEquals("2018-08-04", date2.toString());
    }
}
