package com.porch.peerprogram.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/honey-do")
@Consumes("application/json")
@Produces("application/json")
public class HoneyDoListResource {

    private List<List<String>> allItems;
    private List<String> notStarted;
    private List<String> inProgress;
    private List<String> completed;

    public HoneyDoListResource() {
        allItems = new ArrayList<List<String>>();

        notStarted = new ArrayList<String>();
        inProgress = new ArrayList<String>();
        completed = new ArrayList<String>();

        allItems.add(notStarted);
        allItems.add(inProgress);
        allItems.add(completed);
    }

    @GET
    public Response getAllLists() {
        return Response.ok(allItems).build();
    }

    @GET
    @Path("/getCompleted")
    public Response getCompleted() {
        return Response.ok(completed).build();
    }

    @GET
    @Path("/getNotStarted")
    public Response getNotStarted() {
        return Response.ok(notStarted).build();
    }

    @Path("/getInProgress")
    @GET
    public Response getInProgress() {
        return Response.ok(inProgress).build();
    }

    @POST
    public Response addNewItem(@QueryParam("item") String item) {
        notStarted.add(item);
        return Response.ok().build();
    }

    private Response moveToNextStage(String item, int index) {
        if (allItems.get(index).remove(item)) {
            allItems.get(index + 1).add(item);
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Path("/markStarted")
    public Response markStarted(@QueryParam("item") String item) {
        return moveToNextStage(item, 0);
    }

    @POST
    @Path("/markCompleted")
    public Response markCompleted(@QueryParam("item") String item) {
        return moveToNextStage(item, 1);
    }

    private boolean moveToTopGeneric(String item, int index) {
        if (allItems.get(index).remove(item)) {
            allItems.get(index).add(0, item);
            return true;
        }

        return false;
    }

    @PUT
    @Path("/moveToTop")
    public Response moveToTop(@QueryParam("item") String item) {
        if (moveToTopGeneric(item, 0) || moveToTopGeneric(item, 1)) {
            return Response.ok().build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    private boolean deleteGeneric(String item, int index) {
        return allItems.get(index).remove(item);
    }

    @DELETE
    public Response deleteItem(@QueryParam("item") String item) {
        if (deleteGeneric(item, 0) || deleteGeneric(item, 1) || deleteGeneric(item, 2)) {
            return Response.ok().build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
