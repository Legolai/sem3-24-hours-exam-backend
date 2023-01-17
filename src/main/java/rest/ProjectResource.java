package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dtos.*;
import errorhandling.API_Exception;
import errorhandling.GenericExceptionMapper;
import services.ProjectService;
import utils.EMF_Creator;
import utils.GsonLocalDateTime;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Path("projects")
public class ProjectResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final ProjectService PROJECT_SERVICE = ProjectService.getInstance(EMF);

    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTime()).setPrettyPrinting().create();

    @GET
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<ProjectDTO> projectDTOS = PROJECT_SERVICE.getAll();
        return Response.ok().entity(GSON.toJson(projectDTOS)).build();
    }

    @POST
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProject(String content) throws API_Exception {
        String projectName;
        String projectDescription;
        Integer accountId;
        try {
            JsonObject json = JsonParser.parseString(content).getAsJsonObject();
            projectName = json.get("projectName").getAsString();
            projectDescription = json.get("projectDescription").getAsString();
            accountId = json.get("accountId").getAsInt();
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Supplied", 400, e);
        }

        try {
            ProjectDTO projectDTO = PROJECT_SERVICE.create(new ProjectCreateDTO(projectName, projectDescription, accountId));
            return Response.ok().entity(GSON.toJson(projectDTO)).build();

        } catch (Exception ex) {
            Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new API_Exception("Failed to create a new Project!");

    }

    @GET
    @RolesAllowed({"admin", "developer"})
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFullDetaildProject(@PathParam("id") Integer projectId) {
        ProjectFullDetailDTO projectDTO = PROJECT_SERVICE.getFullDetailedProject(projectId);
        return Response.ok().entity(GSON.toJson(projectDTO)).build();
    }


    @GET
    @RolesAllowed("admin")
    @Path("/account/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllByAccountId(@PathParam("id") Integer accountId) {
        List<ProjectDTO> projectDTOS = PROJECT_SERVICE.getAllByAccountId(accountId);
        return Response.ok().entity(GSON.toJson(projectDTOS)).build();
    }

    @GET
    @RolesAllowed("developer")
    @Path("developer/account/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDeveloperRelatedByAccountId(@PathParam("id") Integer accountId) {
        List<ProjectDTO> projectDTOS = PROJECT_SERVICE.getAllDevloperRelatedByAccountId(accountId);
        return Response.ok().entity(GSON.toJson(projectDTOS)).build();
    }

    @GET
    @RolesAllowed("admin")
    @Path("/{id}/invoice")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInvoice(@PathParam("id") Integer projectId) {
        InvoiceDTO invoiceDTO = PROJECT_SERVICE.getProjectInvoice(projectId);
        return Response.ok().entity(GSON.toJson(invoiceDTO)).build();
    }


    @POST
    @RolesAllowed("admin")
    @Path("/{id}/developers")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDevelopersToProject(@PathParam("id") Integer projectId, String content) {
        List<Integer> ids = Arrays.stream(GSON.fromJson(content, Integer[].class)).collect(Collectors.toList());
        PROJECT_SERVICE.addDevelopersToProject(ids, projectId);
        return Response.ok().build();
    }

}


