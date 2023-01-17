package entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQuery(name = "project.deleteAllRows", query = "DELETE from Project ")
@Table(name = "Projects")
public class Project {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "project_ID")
    private Integer projectId;
    @Basic
    @Column(name = "project_name")
    private String projectName;
    @Basic
    @Column(name = "project_description")
    private String projectDescription;
    @Basic
    @Column(name = "project_createdAt")
    private LocalDateTime projectCreatedAt;
    @Basic
    @Column(name = "project_updatedAt")
    private LocalDateTime projectUpdatedAt;
    @ManyToOne
    @JoinColumn(name = "account_ID", nullable = false)
    private Account account;

    @ManyToMany(mappedBy = "projects")
    private Set<Developer> developers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "project")
    private Set<Task> tasks;
    @PrePersist
    public void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.projectCreatedAt = now.minusNanos(now.getNano());
        this.projectUpdatedAt = this.projectCreatedAt;
    }

    @PreUpdate
    public void onUpdate() {
        LocalDateTime now = LocalDateTime.now();
        this.projectUpdatedAt = now.minusNanos(now.getNano());
    }

    public Project() {
    }

    public Project(String projectName, String projectDescription, Account account) {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.account = account;
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

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Set<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<Developer> developers) {
        this.developers = developers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return projectId.equals(project.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId);
    }

    public void addDeveloper(Developer developer) {
        developers.add(developer);
    }

    public void addDevelopers(List<Developer> developers) {
        developers.addAll(developers);
    }
}
