package daos;

import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class ProjectDao implements IDao<Project, Integer> {

    private static EntityManagerFactory emf;
    private static ProjectDao instance;

    public ProjectDao() {
    }

    public static ProjectDao getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ProjectDao();
        }
        return instance;
    }

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    @Override
    public Project getById(Integer key) {
        return executeWithClose((em) -> em.find(Project.class, key));
    }

    @Override
    public List<Project> getAll() {
        return executeWithClose((em) -> {
            TypedQuery<Project> query = em.createQuery("SELECT p FROM Project p", Project.class);
            return query.getResultList();
        });
    }


    @Override
    public void deleteById(Integer key) {
        executeInsideTransaction((em) -> {
            Project p = em.find(Project.class, key);
            if (p != null) {
                em.remove(p);
            }
        });
    }
}
