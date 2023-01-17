package services;

import daos.DeveloperDao;
import daos.ProjectDao;
import daos.TaskDao;
import dtos.TaskFullDetailedDTO;
import entities.Task;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class TaskService {

    private static EntityManagerFactory emf;
    private static TaskService instance;

    private static TaskDao taskDao;

    private TaskService() {
    }

    public static TaskService getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            taskDao = TaskDao.getInstance(emf);
            instance = new TaskService();
        }
        return instance;
    }


    public TaskFullDetailedDTO getTaskFullDetailedById (Long taskId){
        List<Task> tasks = taskDao.executeWithClose(em -> {
            TypedQuery<Task> query = em.createQuery("SELECT t from Task t JOIN FETCH t.projectHours ph JOIN FETCH t.subtasks JOIN FETCH ph.developer d JOIN FETCH d.account a WHERE t.taskId = :taskId", Task.class);
            query.setParameter("taskId", taskId);
            return query.getResultList();
        });

        if (tasks.isEmpty()) return null;

        return new TaskFullDetailedDTO(tasks.get(0));
    }

}
