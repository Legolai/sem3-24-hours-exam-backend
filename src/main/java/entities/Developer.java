package entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQuery(name = "developer.deleteAllRows", query = "DELETE from Developer ")
@Table(name = "Developers")
public class Developer {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "developer_ID")
    private Integer developerId;

    @Column(name = "developer_billingPrHour")
    private Double developerBillingPrHour;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_ID", nullable = false)
    private Account account;

    @OneToMany(mappedBy = "developer")
    private Set<ProjectHour> projectHours = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "developers_has_projects",
            joinColumns = @JoinColumn(name = "developer_ID"),
            inverseJoinColumns = @JoinColumn(name = "project_ID"))
    private Set<Project> projects  = new LinkedHashSet<>();


    public Developer() {
    }

    public Developer(Double developerBillingPrHour, Account account) {
        this.developerBillingPrHour = developerBillingPrHour;
        this.account = account;
    }

    public Integer getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(Integer developerId) {
        this.developerId = developerId;
    }

    public Double getDeveloperBillingPrHour() {
        return developerBillingPrHour;
    }

    public void setDeveloperBillingPrHour(Double developerBillingPrHour) {
        this.developerBillingPrHour = developerBillingPrHour;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Set<ProjectHour> getProjectHours() {
        return projectHours;
    }

    public void setProjectHours(Set<ProjectHour> projectHours) {
        this.projectHours = projectHours;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Developer developer = (Developer) o;
        return developerId.equals(developer.developerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(developerId);
    }

    public void addProject(Project project) {
        projects.add(project);
    }
}
