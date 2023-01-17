package daos;

import entities.Account;
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


class AccountDaoTest {

    private static EntityManagerFactory emf;
    private static AccountDao facade;

    private Account account1;
    private Account account2;


    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = AccountDao.getInstance(emf);
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

            em.persist(role1);
            em.persist(role2);
            em.persist(account1);
            em.persist(account2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void create() {
        Account actual = facade.create(new Account("New", "new@gmail.com", "1234501", "text"));
        assertTrue(actual.getAccountId() > 0);
    }

    @Test
    public void get() {
        Account actual = facade.getById(account1.getAccountId());

        assertEquals(account1, actual);
    }

    @Test
    public void getAll() {
        List<Account> actual = facade.getAll();

        assertTrue(actual.contains(account1));
        assertTrue(actual.contains(account2));
    }

    @Test
    public void update() {
        account1.setAccountName("henrik");
        assertDoesNotThrow(() -> facade.update(account1));
        assertEquals(account1.getAccountName(), facade.getById(account1.getAccountId()).getAccountName());

    }

    @Test
    public void deleteById() {
        assertDoesNotThrow(() -> facade.deleteById(account1.getAccountId()));
        assertNull(facade.getById(account1.getAccountId()));
    }

}