package org.example.resources;

import org.example.api.TodoModel;
import org.example.dao.TodoDb;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {

    private final Validator validator;

    public TodoResource(Validator validator){
        this.validator = validator;
    }

    @GET
    public Response getTodos() {
        return Response.ok(TodoDb.getTodos()).build();
    }

    @GET
    @Path("/{id}")
    public Response getTodo(@PathParam("id") int id) {
        TodoModel todo = TodoDb.getTodo(id);
        if (todo != null)
            return Response.ok(todo).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response addTodo(TodoModel todo) throws URISyntaxException {
        Random rnd = new Random();
        int n = 10000000 + rnd.nextInt(90000000);
        todo.setId(n);

        // validation
        Set<ConstraintViolation<TodoModel>> violations = validator.validate(todo);
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<TodoModel> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(validationMessages).build();
        }

        TodoModel e = TodoDb.getTodo(todo.getId());
        if (e == null) {
            TodoDb.addTodo(todo);
            return getTodo(n);
        } else
            return Response.status(Response.Status.CONFLICT).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateEmployeeById(@PathParam("id") Integer id, TodoModel todo) {
        // validation
        Set<ConstraintViolation<TodoModel>> violations = validator.validate(todo);
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<TodoModel> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(validationMessages).build();
        }
        TodoModel e = TodoDb.getTodo(id);
        if (e != null) {
            todo.setId(id);
            TodoDb.updateTodo(TodoDb.getIndex(id), todo);
            return Response.ok(todo).build();
        } else return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response removeEmployeeById(@PathParam("id") Integer id) {
        TodoModel todo = TodoDb.getTodo(id);
        if (todo != null) {
            TodoDb.deleteTodo(TodoDb.getIndex(id));
            return Response.ok(Response.Status.OK).build();
        } else
            return Response.status(Response.Status.NOT_FOUND).build();
    }

}
