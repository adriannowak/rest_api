package pl.anowak.service;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
@Provider
@PreMatching

public class PreFilter implements ContainerResponseFilter {
 
 
    @Override
    public void filter( ContainerRequestContext requestCtx, ContainerResponseContext responseCtx ) throws IOException {
    	addIfNotExists( "Access-Control-Allow-Origin", "*" ,responseCtx);    // You may further limit certain client IPs with Access-Control-Allow-Origin instead of '*'
    	addIfNotExists( "Access-Control-Allow-Credentials", "true",responseCtx);
    	addIfNotExists( "Access-Control-Allow-Methods", "GET, POST, DELETE, PUT",responseCtx);
    	addIfNotExists( "Access-Control-Allow-Headers", "origin, content-type, accept, authorization, x-auth-token",responseCtx);
    }

    private void addIfNotExists(String key, String value,ContainerResponseContext response) {
    	if(!response.getHeaders().containsKey(key)) {
    		response.getHeaders().add(key, value);
    	}
    }
}