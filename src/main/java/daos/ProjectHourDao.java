package daos;

import entities.ProjectHour;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class ProjectHourDao implements IDao<ProjectHour, Integer> {
    private static EntityManagerFactory emf;
    private static ProjectHourDao instance;

    public ProjectHourDao() {
    }

    public static ProjectHourDao getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ProjectHourDao();
        }
        return instance;
    }

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public ProjectHour getById(Integer key) {
        return executeWithClose((em) -> em.find(ProjectHour.class, key));
    }

    @Override
    public void update(ProjectHour entity){
        executeInsideTransaction((em) -> {
            ProjectHour projectHour = em.find(ProjectHour.class, entity.getProjecthourId());
            projectHour.setProjecthourDescription(entity.getProjecthourDescription());
            projectHour.setProjecthourHoursSpendt(entity.getProjecthourHoursSpendt());
            em.merge(projectHour);
        });
    }

    @Override
    public List<ProjectHour> getAll() {
        return executeWithClose((em) -> {
            TypedQuery<ProjectHour> query = em.createQuery("SELECT ph FROM ProjectHour ph", ProjectHour.class);
            return query.getResultList();
        });
    }

    @Override
    public void deleteById(Integer key) {
        executeInsideTransaction((em) -> {
            ProjectHour a = em.find(ProjectHour.class, key);
            if (a != null) {
                em.remove(a);
            }
        });
    }
}
