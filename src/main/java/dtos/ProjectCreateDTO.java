package dtos;

import entities.Account;
import entities.Project;

public class ProjectCreateDTO {

    private String projectName;
    private String projectDescription;
    private Integer accountId;


    public ProjectCreateDTO(String projectName, String projectDescription, Integer accountId) {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.accountId = accountId;
    }

    public Project toEntity() {
        Account account = new Account();
        account.setAccountId(accountId);
        Project project = new Project(projectName, projectDescription, account);
        return project;
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

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
}
