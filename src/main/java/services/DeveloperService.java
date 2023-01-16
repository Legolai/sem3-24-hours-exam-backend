package services;

import daos.DeveloperDao;
import daos.ProjectDao;
import daos.ProjectHourDao;
import dtos.DeveloperMiniDTO;
import dtos.ProjectHourCreateDTO;
import dtos.ProjectHourDTO;
import entities.Developer;
import entities.ProjectHour;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class DeveloperService {

    private static EntityManagerFactory emf;
    private static DeveloperService instance;

    private static DeveloperDao developerDao;

    private static ProjectHourDao projectHourDao;

    private DeveloperService() {
    }

    public static DeveloperService getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            developerDao = DeveloperDao.getInstance(emf);
            projectHourDao = ProjectHourDao.getInstance(emf);
            instance = new DeveloperService();
        }
        return instance;
    }

    public ProjectHourDTO createProjectHour(ProjectHourCreateDTO dto) {
        ProjectHour projectHour = projectHourDao.create(dto.toEntity());
        return new ProjectHourDTO(projectHour);
    }

    public List<ProjectHourDTO> getAllProjectHoursForProject(Integer projectId, Integer devId) {
        List<ProjectHour> projectHours = projectHourDao.executeWithClose((em) -> {
            TypedQuery<ProjectHour> query = em.createQuery("SELECT ph FROM ProjectHour ph WHERE ph.developer.developerId = :devId AND ph.task.project.projectId = :projectId", ProjectHour.class);
            query.setParameter("devId", devId);
            query.setParameter("projectId", projectId);
            return query.getResultList();
        });
        return ProjectHourDTO.listToDto(projectHours);
    }

    public List<DeveloperMiniDTO> getDevelopersNotInProject(Integer projectId){
        List<Developer> projectHours = developerDao.executeWithClose((em) -> {
            TypedQuery<Developer> query = em.createQuery("SELECT d FROM Developer d INNER JOIN d.account a INNER JOIN d.projects p WHERE p.projectId != :projectId ", Developer.class);
            query.setParameter("projectId", projectId);
            return query.getResultList();
        });
        return DeveloperMiniDTO.listToDto(projectHours);
    }

    public void updateProjectHour(ProjectHourCreateDTO dto) {
        projectHourDao.update(dto.toEntity());
    }

    public void deleteProjectHour(Integer projectHourId) {
        projectHourDao.deleteById(projectHourId);
    }
}