/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.anowak.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import pl.anowak.PermitAuthorized;
import pl.anowak.data.MemberRepository;
import pl.anowak.model.Member;
import pl.anowak.model.User;
import pl.anowak.model.auth.ApiLoginElement;
import pl.anowak.model.auth.AuthAccessElement;
import pl.anowak.service.AuthService;
import pl.anowak.service.MemberRegistration;

@Path("/auth")
@RequestScoped
@Stateful

public class MemberService {
    @Inject
    private Logger log;

    @Inject
    private MemberRepository repository;

    @Inject
    MemberRegistration registration;

    @Inject
    AuthService authService;
    
    @Context 
    SecurityContext sc;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    
    public Response login(ApiLoginElement login) {
    	return returnIfPresent(authService.login(login));
    }
    
    
    private static <T> Response returnIfPresent(Optional<T> login) {

    	if(login.isPresent()) {
    		return Response.ok().
    				entity(login.get()).build();
    	}
    	return Response.status(Status.NOT_FOUND).build();
	}

}
