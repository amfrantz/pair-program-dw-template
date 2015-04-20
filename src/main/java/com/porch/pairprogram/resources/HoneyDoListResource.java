package com.porch.pairprogram.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("/honey-do")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HoneyDoListResource {
    private int idCounter;
    private Map<ItemState, List<Item>> storage;

    public HoneyDoListResource() {
        this.idCounter = 1;
        this.storage = new HashMap<ItemState, List<Item>>();
        this.storage.put(ItemState.TODO, new ArrayList<Item>());
        this.storage.put(ItemState.IN_PROGRESS, new ArrayList<Item>());
        this.storage.put(ItemState.DONE, new ArrayList<Item>());
    }

    @POST
    public Response simpleAdd(String description) {
        Item item = new Item(description);
        this.storage.get(ItemState.TODO).add(item);
        return Response.ok(item.getId()).build();
    }

    @GET
    public Response simpleGet() {
        return Response.ok(storage).build();
    }

    @DELETE
    @Path("/{id}")
    public Response simpleRemove(@PathParam("id") int id) {
        boolean deleted = false;
        for (ItemState state : storage.keySet()) {
            List<Item> list = storage.get(state);
            if (removeFromList(id, list) != null) {
                deleted = true;
                break;
            }
        }

        return Response.ok(deleted).build();
    }

    @PUT
    @Path("/move-to-top/{id}")
    public Response simpleReorder(@PathParam("id") int id) {
        Item item = null;
        for (ItemState state : storage.keySet()) {
            List<Item> list = storage.get(state);
            Item result = removeFromList(id, list);
            if (result != null) {
                item = result;
                break;
            }
        }
        if (item == null) {
            return Response.status(404).build();
        } else {
            item.updateModifiedDate();
            storage.get(item.state).add(item);
            return Response.ok("Reordered").build();
        }
    }

    @PUT
    @Path("/change-state/{id}/{state}")
    public Response simpleChangeState(@PathParam("id") int id, @PathParam("state") ItemState state) {
        Item item = null;
        for (ItemState currentState : storage.keySet()) {
            List<Item> list = storage.get(currentState);
            Item result = removeFromList(id, list);
            if (result != null) {
                item = result;
                break;
            }
        }

        if (item == null) {
            return Response.status(404).build();
        } else {
            item.updateModifiedDate();
            storage.get(state).add(item);
            return Response.ok("State Changed").build();
        }
    }

    private Item removeFromList(int id, List<Item> list) {
        for (int index = 0; index < list.size(); index++) {
            Item current = list.get(index);
            if (current.getId() == id) {
                list.remove(current);
                return current;
            }
        }
        return null;
    }

    private class Item {
        public int getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        public Date getCreated() {
            return created;
        }

        public Date getModified() {
            return modified;
        }

        public Date getCompleted() {
            return completed;
        }

        public ItemState getState() {
            return state;
        }

        int id;
        String description;
        Date created, modified, completed;
        ItemState state;

        public Item(String description) {
            this.description = description;
            this.id = idCounter++;
            this.state = ItemState.TODO;
            this.created = new Date();
            this.modified = this.created;
        }

        public void changeItemState(ItemState state) {
            this.state = state;
            this.updateModifiedDate();
        }

        public void updateModifiedDate() {
            this.modified = new Date();
        }



    }

    private enum ItemState {
        TODO,
        IN_PROGRESS,
        DONE
    }

}
