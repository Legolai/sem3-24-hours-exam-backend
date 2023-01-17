package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.ProjectFullDetailDTO;
import dtos.TaskFullDetailedDTO;
import services.ProjectService;
import services.TaskService;
import utils.EMF_Creator;
import utils.GsonLocalDateTime;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;

@Path("tasks")
public class TaskResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final TaskService TASK_SERVICE = TaskService.getInstance(EMF);

    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTime()).setPrettyPrinting().create();

    @GET
    @RolesAllowed({"admin", "developer"})
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFullDetailedTask(@PathParam("id") Long taskId) {
        TaskFullDetailedDTO dto = TASK_SERVICE.getTaskFullDetailedById(taskId);
        return Response.ok().entity(GSON.toJson(dto)).build();
    }

}
