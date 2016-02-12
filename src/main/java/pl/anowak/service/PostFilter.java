package pl.anowak.service;

import java.io.IOException;
import java.lang.reflect.Method;
import java.security.Principal;

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
    
    @Inject AuthTokenService auth;
    
    @Inject MemberRepository mr;
    @Context
    private HttpServletRequest request;

    private static final Response ACCESS_UNAUTHORIZED = Response.status(Response.Status.UNAUTHORIZED).entity("Not authorized.").build();

    @Override
    public void filter( ContainerRequestContext requestCtx ) throws IOException {
    	System.err.println(requestCtx.getMethod());
        String path = requestCtx.getUriInfo().getPath();
        System.err.println(path);
//        // IMPORTANT!!! First, Acknowledge any pre-flight test from browsers for this case before validating the headers (CORS stuff)
        if ( requestCtx.getRequest().getMethod().equals( "OPTIONS" ) ) {
            requestCtx.abortWith( Response.status( Response.Status.OK ).build() );
 
            return;
        }
        
      String serviceKey = requestCtx.getHeaderString( CUSTOM_HEADER.AUTH_TOKEN.getHeader() );
      System.err.println("MAM SERVICE KEY!!"+serviceKey);
      Method method = resourceInfo.getResourceMethod();
      
      if(method.isAnnotationPresent(PermitUnauthorized.class) && (serviceKey != null || auth.tokenExists(serviceKey))) {
    	  requestCtx.abortWith(ACCESS_UNAUTHORIZED);

      }
      else if(method.isAnnotationPresent(PermitAuthorized.class) && (serviceKey == null || !auth.tokenExists(serviceKey))) {
    	  requestCtx.abortWith(ACCESS_UNAUTHORIZED);

      }
      
          String scheme = requestCtx.getUriInfo().getRequestUri().getScheme();
          System.err.println(scheme);
          System.err.println("XXXXXXXXXXXXXXX");
          requestCtx.setSecurityContext(new SecurityContext() {
      		
      		@Override
      		public boolean isUserInRole(String role) {
      			// TODO Auto-generated method stub
      			return true;
      		}
      		
      		@Override
      		public boolean isSecure() {
      			// TODO Auto-generated method stub
      			return true;
      		}
      		
      		@Override
      		public Principal getUserPrincipal() {
      			// TODO Auto-generated method stub
      			return new Principal() {
      				
      				@Override
      				public String getName() {
      					// TODO Auto-generated method stub
      					return "asdsadasdasd";
      				}
      			};
      		}
      		
      		@Override
      		public String getAuthenticationScheme() {
      			// TODO Auto-generated method stub
      			return SecurityContext.BASIC_AUTH;
      		}
      	});

// 
//        // Then check is the service key exists and is valid.
//        DemoAuthenticator demoAuthenticator = DemoAuthenticator.getInstance();
//        String serviceKey = requestCtx.getHeaderString( DemoHTTPHeaderNames.SERVICE_KEY );
// 
//        if ( !demoAuthenticator.isServiceKeyValid( serviceKey ) ) {
//            // Kick anyone without a valid service key
//            requestCtx.abortWith( Response.status( Response.Status.UNAUTHORIZED ).build() );
// 
//            return;
//        }
// 
//        // For any pther methods besides login, the authToken must be verified
//        if ( !path.startsWith( "/demo-business-resource/login/" ) ) {
//            String authToken = requestCtx.getHeaderString( DemoHTTPHeaderNames.AUTH_TOKEN );
// 
//            // if it isn't valid, just kick them out.
//            if ( !demoAuthenticator.isAuthTokenValid( serviceKey, authToken ) ) {
//                requestCtx.abortWith( Response.status( Response.Status.UNAUTHORIZED ).build() );
//            }
//        }
    }
}