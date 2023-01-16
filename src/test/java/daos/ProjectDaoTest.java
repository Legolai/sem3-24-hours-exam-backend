package daos;

import entities.Account;
import entities.Project;
import entities.Role;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectDaoTest {
    private static EntityManagerFactory emf;
    private static ProjectDao facade;


    private Project project1;
    private Project project2;
    private Account account1;


    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = ProjectDao.getInstance(emf);
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
            account1 = new Account("Jens", "jens@email.com", "12345678", "test1");
            account1.addRole(role1);
            project1 = new Project("ProjectTest", "Some Project", account1);
            project2 = new Project("ProjectTest2", "Some Project2", account1);

            em.persist(role1);
            em.persist(account1);
            em.persist(project1);
            em.persist(project2);


            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void create() {
        Project actual = facade.create(new Project("Project3", "hello world", account1));
        assertTrue( actual.getProjectId() > 0);
    }

    @Test
    public void get() {
        Project actual = facade.getById(project1.getProjectId());

        assertEquals(project1, actual);
    }

    @Test
    public void getAll() {
        List<Project> actual = facade.getAll();

        assertTrue(actual.contains(project1));
        assertTrue(actual.contains(project2));
    }

    @Test
    public void update() {
        project1.setProjectDescription("Something new");
        assertDoesNotThrow(() -> facade.update(project1));
        assertEquals(project1.getProjectDescription(), facade.getById(project1.getProjectId()).getProjectDescription());

    }

    @Test
    public void deleteById() {
        assertDoesNotThrow(() -> facade.deleteById(project2.getProjectId()));
        assertEquals(null, facade.getById(project2.getProjectId()));
    }

}