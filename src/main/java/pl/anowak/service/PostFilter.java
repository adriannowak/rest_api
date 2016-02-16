package pl.anowak.service;

import java.io.IOException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import pl.anowak.PermitAuthorized;
import pl.anowak.PermitUnauthorized;
import pl.anowak.data.MemberRepository;
import pl.anowak.model.CUSTOM_HEADER;

@Provider
public class PostFilter implements ContainerRequestFilter {

	@Context
	private ResourceInfo resourceInfo;

	@Inject
	SecurityContextService securityContext;

	private static final Response ACCESS_UNAUTHORIZED = Response
			.status(Response.Status.UNAUTHORIZED).entity("{\"errorMessage\":\"Not authorized\"}")
			.build();

	@Override
	public void filter(ContainerRequestContext requestCtx) throws IOException {

		if (requestCtx.getRequest().getMethod().equals("OPTIONS")) {
			requestCtx.abortWith(Response.status(Response.Status.OK).build());
			return;
		}

		String serviceKey = CUSTOM_HEADER.AUTH_TOKEN.getHeader(requestCtx);
		
		System.err.println("MAM SERVICE KEY!!" + serviceKey);

		if (serviceKey != null) {
			Optional<SecurityContext> ctx = securityContext.createSecurityContext(serviceKey);
			if (ctx.isPresent()) {
				requestCtx.setSecurityContext(ctx.get());
			} else {
				requestCtx.abortWith(ACCESS_UNAUTHORIZED);
			}
		}
		else {
			Method method = resourceInfo.getResourceMethod();
			if (method.isAnnotationPresent(PermitAuthorized.class)) {
				requestCtx.abortWith(ACCESS_UNAUTHORIZED);
	
			}
		}

//
//		if (method.isAnnotationPresent(PermitUnauthorized.class)
//				&& (serviceKey != null || auth.tokenExists(serviceKey))) {
//			requestCtx.abortWith(ACCESS_UNAUTHORIZED);
//
//		} else 
	}
}