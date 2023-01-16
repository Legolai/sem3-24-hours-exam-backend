package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.DeveloperMiniDTO;
import services.DeveloperService;
import services.ProjectService;
import utils.EMF_Creator;
import utils.GsonLocalDateTime;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;

@Path("developers")
public class DeveloperResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final DeveloperService DEVELOPER_SERVICE = DeveloperService.getInstance(EMF);

    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTime()).setPrettyPrinting().create();



    @GET
    @Path("/project/{id}/not")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDevelopersNotInProject(@PathParam("id") Integer projectId){
        List<DeveloperMiniDTO> dtos = DEVELOPER_SERVICE.getDevelopersNotInProject(projectId);
        return Response.ok().entity(dtos).build();
    }
}
