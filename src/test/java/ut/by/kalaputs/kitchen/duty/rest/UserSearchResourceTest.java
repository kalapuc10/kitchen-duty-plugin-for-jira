package ut.by.kalaputs.kitchen.duty.rest;


import by.kalaputs.kitchen.duty.rest.UserSearchResource;
import by.kalaputs.kitchen.duty.rest.model.UserSearchResourceModel;
import com.atlassian.jira.bc.user.search.UserSearchParams;
import com.atlassian.jira.bc.user.search.UserSearchService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class UserSearchResourceTest {

    @Before
    public void setup() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void messageIsValid() {
        final List<String> mockedUsers = new ArrayList<>();
        mockedUsers.add("bob");
        mockedUsers.add("sue");

        UserSearchService mockedUserSearchService = Mockito.mock(UserSearchService.class);
        when(mockedUserSearchService.findUserNames(anyString(), any(UserSearchParams.class))).thenReturn(mockedUsers);

        UserSearchResource resource = new UserSearchResource(mockedUserSearchService);

        Response response = resource.searchUsers("bo", null);
        @SuppressWarnings("unchecked") final List<UserSearchResourceModel> users = (List<UserSearchResourceModel>) response.getEntity();

        assertEquals("should contain bob", "bob", users.get(0).getText());
    }
}
