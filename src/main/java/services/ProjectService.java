package services;

import daos.DeveloperDao;
import daos.ProjectDao;
import daos.ProjectHourDao;
import dtos.InvoiceDTO;
import dtos.ProjectCreateDTO;
import dtos.ProjectDTO;
import dtos.ProjectFullDetailDTO;
import entities.Developer;
import entities.Project;
import entities.ProjectHour;

import javax.persistence.EntityManagerFactory;
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
        Project project = projectDao.executeWithClose((em) -> {
        TypedQuery<Project> query = em.createQuery("SELECT p FROM Project p join fetch p.developers d JOIN FETCH d.projectHours ph WHERE p.projectId = :projectId", Project.class);
            query.setParameter("projectId", projectId);
            List<Project> projects = query.getResultList();

            if(projects.get(0) == null) return null;

            Project refreshed = projects.get(0);
            refreshed.getDevelopers().forEach(developer -> {
                TypedQuery<ProjectHour> query2 = em.createQuery("SELECT p FROM ProjectHour p join p.developer d WHERE d.developerId = :devId", ProjectHour.class);
                query2.setParameter("devId", developer.getDeveloperId());
                developer.setProjectHours(query2.getResultList().stream().collect(Collectors.toSet()));
            });

            return refreshed;

        });

        if(project == null) return null;

        return new InvoiceDTO(project);
    }
}
