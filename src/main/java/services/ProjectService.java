package services;

import daos.DeveloperDao;
import daos.ProjectDao;
import dtos.InvoiceDTO;
import dtos.ProjectCreateDTO;
import dtos.ProjectDTO;
import dtos.ProjectFullDetailDTO;
import entities.Developer;
import entities.Project;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectService {

    private static EntityManagerFactory emf;
    private static ProjectService instance;

    private static ProjectDao projectDao;
    private static DeveloperDao developerDao;

    private ProjectService() {
    }

    public static ProjectService getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            projectDao = ProjectDao.getInstance(emf);
            developerDao = DeveloperDao.getInstance(emf);
            instance = new ProjectService();
        }
        return instance;
    }


    public List<ProjectDTO> getAll() {
        return ProjectDTO.listToDto(projectDao.getAll());
    }

    public List<ProjectDTO> getAllByAccountId(Integer accountId) {
        List<Project> projects = projectDao.executeWithClose((em) -> {
            TypedQuery<Project> query = em.createQuery("SELECT p FROM Project p WHERE p.account.accountId = :accountId", Project.class);
            query.setParameter("accountId", accountId);
            return  query.getResultList();
        });

        return ProjectDTO.listToDto(projects);
    }

    public List<ProjectDTO> getAllDevloperRelatedByAccountId(Integer accountId) {
        List<Project> projects = projectDao.executeWithClose((em) -> {
            TypedQuery<Project> query = em.createQuery("SELECT p FROM Developer d JOIN d.account a JOIN d.projects p WHERE a.accountId = :accountId", Project.class);
            query.setParameter("accountId", accountId);
            return  query.getResultList();
        });

        return ProjectDTO.listToDto(projects);
    }

    public ProjectFullDetailDTO getFullDetailedProject(Integer projectId) {
        List<Project> projects = projectDao.executeWithClose((em) -> {
            TypedQuery<Project> query = em.createQuery("SELECT p FROM Project p JOIN FETCH p.developers d JOIN FETCH p.account a JOIN FETCH p.tasks t WHERE p.projectId = :projectId", Project.class);
            query.setParameter("projectId", projectId);
            return  query.getResultList();
        });

        if(projects.isEmpty()) return null;

        return new ProjectFullDetailDTO(projects.get(0));
    }

    public ProjectDTO create(ProjectCreateDTO dto) {
        Project project = projectDao.create(dto.toEntity());
        return new ProjectDTO(project);
    }

    public void addDevelopersToProject(List<Integer> devs, Integer projectId) {

        Project project = projectDao.getById(projectId);
        List<Developer> developers = devs.stream().map(devId -> {
            Developer developer = developerDao.getById(devId);
            developer.addProject(project);
            developerDao.update(developer);
            return developer;
        }).collect(Collectors.toList());

        project.addDevelopers(developers);

        projectDao.update(project);
    }

    public InvoiceDTO getProjectInvoice(Integer projectId){
        List<Project> projects = projectDao.executeWithClose((em) -> {
            TypedQuery<Project> query = em.createQuery("SELECT p FROM Project p JOIN FETCH p.developers d JOIN FETCH d.projectHours ph WHERE p.projectId = :projectId", Project.class);
            query.setParameter("projectId", projectId);
            return query.getResultList();
        });

        if(projects.isEmpty()) return null;
        return new InvoiceDTO(projects.get(0));
    }
}
