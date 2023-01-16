package entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NamedQuery(name = "projectHour.deleteAllRows", query = "DELETE from ProjectHour ")
@Table(name = "ProjectHours")
public class ProjectHour {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "projecthour_ID")
    private Integer projecthourId;
    @Basic
    @Column(name = "projecthour_hoursSpendt")
    private Double projecthourHoursSpendt;
    @Basic
    @Column(name = "projecthour_description")
    private Integer projecthourDescription;
    @Basic
    @Column(name = "projecthour_createdAt")
    private LocalDateTime projecthourCreateAt;
    @Basic
    @Column(name = "projecthour_updatedAt")
    private LocalDateTime projecthourUpdateAt;

    @ManyToOne
    @JoinColumn(name = "task_ID", nullable = false)
    private Task task;
    @ManyToOne
    @JoinColumn(name = "developer_ID", nullable = false)
    private Developer developer;

    @PrePersist
    public void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.projecthourCreateAt = now.minusNanos(now.getNano());
        this.projecthourUpdateAt = this.projecthourCreateAt;
    }

    @PreUpdate
    public void onUpdate() {
        LocalDateTime now = LocalDateTime.now();
        this.projecthourUpdateAt = now.minusNanos(now.getNano());
    }


    public ProjectHour() {
    }

    public ProjectHour(Integer projecthourId, Double projecthourHoursSpendt, Integer projecthourDescription, Task task, Developer developer) {
        this.projecthourId = projecthourId;
        this.projecthourHoursSpendt = projecthourHoursSpendt;
        this.projecthourDescription = projecthourDescription;
        this.task = task;
        this.developer = developer;
    }

    public Integer getProjecthourId() {
        return projecthourId;
    }

    public void setProjecthourId(Integer projecthourId) {
        this.projecthourId = projecthourId;
    }

    public Double getProjecthourHoursSpendt() {
        return projecthourHoursSpendt;
    }

    public void setProjecthourHoursSpendt(Double projecthourHoursSpendt) {
        this.projecthourHoursSpendt = projecthourHoursSpendt;
    }

    public Integer getProjecthourDescription() {
        return projecthourDescription;
    }

    public void setProjecthourDescription(Integer projecthourDescription) {
        this.projecthourDescription = projecthourDescription;
    }

    public LocalDateTime getProjecthourCreateAt() {
        return projecthourCreateAt;
    }

    public void setProjecthourCreateAt(LocalDateTime projecthourCreateAt) {
        this.projecthourCreateAt = projecthourCreateAt;
    }

    public LocalDateTime getProjecthourUpdateAt() {
        return projecthourUpdateAt;
    }

    public void setProjecthourUpdateAt(LocalDateTime projecthourUpdateAt) {
        this.projecthourUpdateAt = projecthourUpdateAt;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectHour that = (ProjectHour) o;
        return projecthourId.equals(that.projecthourId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projecthourId);
    }
}
