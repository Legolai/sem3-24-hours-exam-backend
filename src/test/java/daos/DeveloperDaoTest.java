package daos;

import entities.Account;
import entities.Developer;
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

class DeveloperDaoTest {
    private static EntityManagerFactory emf;
    private static DeveloperDao facade;

    private Developer dev1;
    private Developer dev2;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = DeveloperDao.getInstance(emf);
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


            Role role2 = new Role("developer");
            Account account1 = new Account("Jens", "jens@email.com", "12345678", "test1");
            account1.addRole(role2);
            Account account2 = new Account("Peter", "peter@email.com", "22334455", "test2");
            account2.addRole(role2);
            dev1 = new Developer(100.0, account1);
            dev2 = new Developer(100.0, account2);

            em.persist(role2);
            em.persist(account1);
            em.persist(account2);
            em.persist(dev1);
            em.persist(dev2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void create() {
        Account account = new Account("New", "new@gmail.com", "1234501", "text");
        facade.executeInsideTransaction((em) -> em.persist(account));
        Developer actual = facade.create(new Developer(400.0, account));
        assertTrue(actual.getDeveloperId() > 0);
    }

    @Test
    public void get() {
        Developer actual = facade.getById(dev1.getDeveloperId());

        assertEquals(dev1, actual);
    }

    @Test
    public void getAll() {
        List<Developer> actual = facade.getAll();

        assertTrue(actual.contains(dev1));
        assertTrue(actual.contains(dev2));
    }

    @Test
    public void update() {
        dev1.setDeveloperBillingPrHour(450.0);
        assertDoesNotThrow(() -> facade.update(dev1));
        assertEquals(dev1.getDeveloperBillingPrHour(), facade.getById(dev1.getDeveloperId()).getDeveloperBillingPrHour());

    }

    @Test
    public void deleteById() {
        assertDoesNotThrow(() -> facade.deleteById(dev1.getDeveloperId()));
        assertNull(facade.getById(dev1.getDeveloperId()));
    }

}