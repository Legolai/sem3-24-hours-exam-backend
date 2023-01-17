package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dtos.*;
import errorhandling.API_Exception;
import errorhandling.GenericExceptionMapper;
import services.DeveloperService;
import utils.EMF_Creator;
import utils.GsonLocalDateTime;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    @POST
    @RolesAllowed("developer")
    @Path("/project-hours")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProjectHours(String content) throws API_Exception {
        Double hoursSpent;
        String description;
        Long taskId;
        Integer accountId;
        try {
            JsonObject json = JsonParser.parseString(content).getAsJsonObject();
            hoursSpent = json.get("hoursSpent").getAsDouble();
            description = json.get("description").getAsString();
            taskId = json.get("taskId").getAsLong();
            accountId = json.get("accountId").getAsInt();
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Supplied", 400, e);
        }

        try {
            DeveloperMiniDTO developer = DEVELOPER_SERVICE.getDeveloperTrimmedByAccountId(accountId);
            ProjectHourCreateDTO projectHourCreateDTO = new ProjectHourCreateDTO(hoursSpent, description, taskId, developer.getDeveloperId());
            ProjectHourDTO dto = DEVELOPER_SERVICE.createProjectHour(projectHourCreateDTO);
            return Response.ok().entity(GSON.toJson(dto)).build();

        } catch (Exception ex) {
            Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new API_Exception("Failed to create a new time tracker!");
    }

    @PUT
    @RolesAllowed("developer")
    @Path("/project-hours/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProjectHour(@PathParam("id") Integer projectHourId, String content) throws API_Exception {
        Double hoursSpent;
        String description;
        Long taskId;
        Integer accountId;
        try {
            JsonObject json = JsonParser.parseString(content).getAsJsonObject();
            hoursSpent = json.get("hoursSpendt").getAsDouble();
            description = json.get("description").getAsString();
            taskId = json.get("taskId").getAsLong();
            accountId = json.get("accountId").getAsInt();
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Supplied", 400, e);
        }

        try {
            DeveloperMiniDTO developer = DEVELOPER_SERVICE.getDeveloperTrimmedByAccountId(accountId);
            ProjectHourUpdateDTO projectHourUpdateDTO = new ProjectHourUpdateDTO(hoursSpent, description, projectHourId, developer.getDeveloperId(), taskId);
            DEVELOPER_SERVICE.updateProjectHour(projectHourUpdateDTO);
            return Response.ok().build();

        } catch (Exception ex) {
            Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new API_Exception("Failed to update a new time tracker!");
    }

    @DELETE
    @RolesAllowed("developer")
    @Path("/project-hours/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProjectHour(@PathParam("id") Integer projectHourId)  {
        DEVELOPER_SERVICE.deleteProjectHour(projectHourId);
        return Response.ok().build();
    }

}
