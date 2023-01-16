package dtos;

import entities.Account;
import entities.Developer;
import entities.Project;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProjectFullDetailDTO {
    private Integer projectId;

    private String projectName;

    private String projectDescription;

    private LocalDateTime projectCreatedAt;

    private LocalDateTime projectUpdatedAt;

    private Integer accountId;
    private String accountName;
    private List<DeveloperMiniDTO> developers;

    private List<TaskMiniDTO> tasks;

    public ProjectFullDetailDTO(Project project) {
        this.projectId = project.getProjectId();
        this.projectName = project.getProjectName();
        this.projectDescription = project.getProjectDescription();
        this.projectCreatedAt = project.getProjectCreatedAt();
        this.projectUpdatedAt = project.getProjectUpdatedAt();
        this.accountId = project.getAccount().getAccountId();
        this.accountName = project.getAccount().getAccountName();
        this.developers = project.getDevelopers().stream().map(DeveloperMiniDTO::new).collect(Collectors.toList());
        this.tasks = project.getTasks().stream().map(TaskMiniDTO::new).collect(Collectors.toList());
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

    public LocalDateTime getProjectCreatedAt() {
        return projectCreatedAt;
    }

    public void setProjectCreatedAt(LocalDateTime projectCreatedAt) {
        this.projectCreatedAt = projectCreatedAt;
    }

    public LocalDateTime getProjectUpdatedAt() {
        return projectUpdatedAt;
    }

    public void setProjectUpdatedAt(LocalDateTime projectUpdatedAt) {
        this.projectUpdatedAt = projectUpdatedAt;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public List<DeveloperMiniDTO> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<DeveloperMiniDTO> developers) {
        this.developers = developers;
    }

    public List<TaskMiniDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskMiniDTO> tasks) {
        this.tasks = tasks;
    }
}
