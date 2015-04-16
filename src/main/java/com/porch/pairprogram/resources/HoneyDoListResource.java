package com.porch.pairprogram.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/honey-do")
public class HoneyDoListResource {

    @GET
    public Response simpleGet() {
        String message = "It works!";
        return Response.ok(message).build();
    }
}
