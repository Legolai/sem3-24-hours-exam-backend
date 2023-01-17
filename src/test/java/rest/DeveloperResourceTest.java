package rest;

import entities.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class DeveloperResourceTest {
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;


    private static EntityManagerFactory emf;

    private Account admin;

    private Developer developer;
    private Project project1;
    private Project project2;

    private ProjectHour projectHour1;
    private Task task1;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();

        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //Delete existing users and roles to get a "fresh" database
            em.createNamedQuery("projectHour.deleteAllRows").executeUpdate();
            em.createNamedQuery("task.deleteAllRows").executeUpdate();
            em.createNamedQuery("project.deleteAllRows").executeUpdate();
            em.createNamedQuery("developer.deleteAllRows").executeUpdate();
            em.createNamedQuery("account.deleteAllRows").executeUpdate();
            em.createNamedQuery("role.deleteAllRows").executeUpdate();

            Role userRole = new Role("developer");
            Role adminRole = new Role("admin");
            em.persist(userRole);
            em.persist(adminRole);

            Account user = new Account("user", "user@email.com", "12345678", "test");
            user.addRole(userRole);
            admin = new Account("admin", "admin@email.com", "22334455", "test");
            admin.addRole(adminRole);


            project1 = new Project("ProjectTest", "Some Project", admin);
            project2 = new Project("ProjectTest2", "Some Project2", admin);

            developer = new Developer(100.0, user);
            project1.addDeveloper(developer);
            developer.addProject(project1);
            task1 = new Task("Do something", "A lot of work", project1);

            projectHour1 = new ProjectHour(10.0, "A lot of work", task1, developer);
            ProjectHour projectHour2 = new ProjectHour(5.0, "some more work", task1, developer);

            em.persist(user);
            em.persist(admin);
            em.persist(project1);
            em.persist(project2);
            em.persist(developer);
            em.persist(task1);
            em.persist(projectHour1);
            em.persist(projectHour2);

            //System.out.println("Saved test data to database");
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    //This is how we hold on to the token after login, similar to that a client must store the token somewhere
    private static String securityToken;

    //Utility method to login and set the returned securityToken
    private static void login(String email, String password) {
        String json = String.format("{email: \"%s\", password: \"%s\"}", email, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        //System.out.println("TOKEN ---> " + securityToken);
    }

    private void logOut() {
        securityToken = null;
    }

    @Test
    public void serverIsRunning() {
        given().when().get("/info").then().statusCode(200);
    }

    @Test
    void getDevelopersNotInProject() {
        login("admin@email.com", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("developers/project/"+project1.getProjectId()+"/not").then()
                .statusCode(200)
                .body("", hasSize(0));
    }

    @Test
    void createProjectHours() {
        login("user@email.com", "test");
        String json = String.format("{hoursSpent: %d, taskId: %d, accountId: %d, description: \"%s\"}", 10, task1.getTaskId(), developer.getAccount().getAccountId(), "I did something");
        given()
                .contentType("application/json")
                .body(json)
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .post("developers/project-hours").then()
                .statusCode(200)
                .body("projecthourId", notNullValue());
    }

    @Test
    void updateProjectHour() {
        login("user@email.com", "test");
        String json = String.format("{hoursSpendt: %d, taskId: %d, accountId: %d, description: \"%s\"}", 40, task1.getTaskId(), developer.getAccount().getAccountId(), "I did something");
        given()
                .contentType("application/json")
                .body(json)
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .put("developers/project-hours/" + projectHour1.getProjecthourId()).then()
                .statusCode(200);

    }

    @Test
    void deleteProjectHour() {
        login("user@email.com", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .delete("developers/project-hours/" + projectHour1.getProjecthourId()).then()
                .statusCode(200);
    }
}