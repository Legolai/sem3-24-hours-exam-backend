package daos;

import entities.Account;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class AccountDao implements IDao<Account, Integer>{

    private static EntityManagerFactory emf;
    private static AccountDao instance;

    public AccountDao() {}
    public static AccountDao getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new AccountDao();
        }
        return instance;
    }

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    @Override
    public Account getById(Integer key) {
        return executeWithClose((em) -> em.find(Account.class, key));
    }

    @Override
    public List<Account> getAll() {
        return executeWithClose((em) -> {
            TypedQuery<Account> query = em.createQuery("SELECT a FROM Account a", Account.class);
            return query.getResultList();
        });
    }

    @Override
    public void deleteById(Integer key) {
        executeInsideTransaction((em) -> {
            Account a = em.find(Account.class, key);
            if (a != null) {
                em.remove(a);
            }
        });
    }


}
