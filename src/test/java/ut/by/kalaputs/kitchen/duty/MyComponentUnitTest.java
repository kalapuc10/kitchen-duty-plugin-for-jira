package ut.by.kalaputs.kitchen.duty;

import org.junit.Test;
import by.kalaputs.kitchen.duty.api.MyPluginComponent;
import by.kalaputs.kitchen.duty.impl.MyPluginComponentImpl;

import static org.junit.Assert.assertEquals;

public class MyComponentUnitTest {
    @Test
    public void testMyName() {
        MyPluginComponent component = new MyPluginComponentImpl(null);
        assertEquals("names do not match!", "myComponent", component.getName());
    }
}
