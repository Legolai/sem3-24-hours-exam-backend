package daos;

import entities.Developer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class DeveloperDao implements IDao<Developer, Integer> {
    private static EntityManagerFactory emf;
    private static DeveloperDao instance;

    public DeveloperDao() {
    }

    public static DeveloperDao getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DeveloperDao();
        }
        return instance;
    }

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public Developer getById(Integer key) {
        return executeWithClose((em) -> em.find(Developer.class, key));
    }

    @Override
    public List<Developer> getAll() {
        return executeWithClose((em) -> {
            TypedQuery<Developer> query = em.createQuery("SELECT d FROM Developer d", Developer.class);
            return query.getResultList();
        });
    }

    @Override
    public void deleteById(Integer key) {
        executeInsideTransaction((em) -> {
            Developer a = em.find(Developer.class, key);
            if (a != null) {
                em.remove(a);
            }
        });
    }
}
