package services;


import daos.TaskDao;
import dtos.TaskFullDetailedDTO;
import entities.ProjectHour;
import entities.Task;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

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
        Task task = taskDao.executeWithClose(em -> {
            TypedQuery<Task> query = em.createQuery("SELECT t from Task t JOIN FETCH t.projectHours ph JOIN FETCH t.subtasks JOIN FETCH ph.developer d JOIN FETCH d.account a WHERE t.taskId = :taskId", Task.class);
            query.setParameter("taskId", taskId);
            List<Task> tasks = query.getResultList();

            if (tasks.isEmpty()) {
                return null;
            }

            Task refreshed = tasks.get(0);

            TypedQuery<ProjectHour> query1 = em.createQuery("SELECT ph from ProjectHour ph JOIN FETCH ph.developer d JOIN FETCH d.account a JOIN FETCH ph.task t WHERE t.taskId = :taskId", ProjectHour.class);
            query1.setParameter("taskId", taskId);
            List<ProjectHour> projectHours = query1.getResultList();
            refreshed.setProjectHours(new HashSet<>(projectHours));

            return refreshed;
        });

        if (task == null) return null;

        return new TaskFullDetailedDTO(task);
    }

}
