package dtos;


import entities.Project;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProjectDTO {

    private Integer projectId;

    private String projectName;

    private String projectDescription;

    public ProjectDTO(Integer projectId, String projectName, String projectDescription) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
    }

    public ProjectDTO(Project project) {
        this.projectId = project.getProjectId();
        this.projectName = project.getProjectName();
        this.projectDescription = project.getProjectDescription();
    }

    public static List<ProjectDTO> listToDto(List<Project> entities) {
        return entities.stream().map(ProjectDTO::new).collect(Collectors.toList());
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectDTO that = (ProjectDTO) o;
        return Objects.equals(projectId, that.projectId) && Objects.equals(projectName, that.projectName) && Objects.equals(projectDescription, that.projectDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, projectName, projectDescription);
    }
}
