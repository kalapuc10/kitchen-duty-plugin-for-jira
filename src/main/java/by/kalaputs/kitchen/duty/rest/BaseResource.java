package by.kalaputs.kitchen.duty.rest;

import by.kalaputs.kitchen.duty.rest.model.RestError;
import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.sal.api.user.UserProfile;

import javax.ws.rs.core.Response;

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
}
