package daos;

import entities.ProjectHour;
import entities.Task;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class TaskDao implements IDao<Task, Integer>{
    private static EntityManagerFactory emf;
    private static TaskDao instance;

    public TaskDao() {
    }

    public static TaskDao getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TaskDao();
        }
        return instance;
    }

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public Task getById(Integer key) {
        return executeWithClose((em) -> em.find(Task.class, key));
    }

    @Override
    public List<Task> getAll() {
        return executeWithClose((em) -> {
            TypedQuery<Task> query = em.createQuery("SELECT t FROM Task t", Task.class);
            return query.getResultList();
        });
    }

    @Override
    public void deleteById(Integer key) {
        executeInsideTransaction((em) -> {
            Task a = em.find(Task.class, key);
            if (a != null) {
                em.remove(a);
            }
        });
    }
}
