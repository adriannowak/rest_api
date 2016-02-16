package pl.anowak.rest;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import pl.anowak.PermitAuthorized;
import pl.anowak.data.UserActRepository;
import pl.anowak.model.User;
import pl.anowak.model.UserLog;
import pl.anowak.service.SecurityContextService;

@Path("/me")
@RequestScoped
@Stateful
public class UserService {

	@Inject
	SecurityContextService contextService;
	
    @Context 
	SecurityContext securityContext;

    @Inject
    UserActRepository uac;
    
	@GET
	@PermitAuthorized
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserLog> getUser() {
		Optional<User> user = contextService.getSecurityContext(securityContext);
		return uac.getAll(user.get());
    }
}
