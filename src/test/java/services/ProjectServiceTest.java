package services;

import dtos.InvoiceDTO;
import dtos.ProjectDTO;
import entities.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectServiceTest {

    private static EntityManagerFactory emf;
    private static ProjectService facade;

    private Account account1;
    private Account account2;

    private Project project;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = ProjectService.getInstance(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        //        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Set up the DataBase in a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("projectHour.deleteAllRows").executeUpdate();
            em.createNamedQuery("task.deleteAllRows").executeUpdate();
            em.createNamedQuery("project.deleteAllRows").executeUpdate();
            em.createNamedQuery("developer.deleteAllRows").executeUpdate();
            em.createNamedQuery("account.deleteAllRows").executeUpdate();
            em.createNamedQuery("role.deleteAllRows").executeUpdate();

            Role role1 = new Role("admin");
            Role role2 = new Role("developer");
            account1 = new Account("Jens", "jens@email.com", "12345678", "test1");
            account1.addRole(role1);
            account2 = new Account("Peter", "peter@email.com", "22334455", "test2");
            account2.addRole(role2);

            project = new Project("Horse", "Some Horse", account1);
            Developer dev = new Developer(100.0, account2);
            project.addDeveloper(dev);
            dev.addProject(project);
            Task task1 = new Task("Do something", "A lot of work", project);

            ProjectHour projectHour1 = new ProjectHour(10.0, "A lot of work", task1, dev);
            ProjectHour projectHour2 = new ProjectHour(5.0, "some more work", task1, dev);

            em.persist(role1);
            em.persist(role2);
            em.persist(account1);
            em.persist(account2);
            em.persist(project);
            em.persist(dev);
            em.persist(task1);
            em.persist(projectHour1);
            em.persist(projectHour2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }


    @Test
    public void getProjects(){
        List<ProjectDTO> actual = facade.getAll();
        assertTrue(actual.contains(new ProjectDTO(project)));
    }
    @Test
    public void getProjectInvoice(){
        InvoiceDTO actual = facade.getProjectInvoice(project.getProjectId());
        System.out.println(actual);
        assertNotEquals(null,actual);
    }

}