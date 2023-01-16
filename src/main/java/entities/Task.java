package entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Entity
@NamedQuery(name = "task.deleteAllRows", query = "DELETE from Task ")
@Table(name = "Tasks")
public class Task {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "task_ID")
    private Long taskId;

    @Column(name = "task_title")
    private String taskTitle;

    @Column(name = "task_description")
    private String taskDescription;

    @Column(name = "task_createdAt")
    private LocalDateTime taskCreatedAt;

    @Column(name = "task_updatedAt")
    private LocalDateTime taskUpdatedAt;
    @OneToMany(mappedBy = "task")
    private Collection<ProjectHour> projectHoursByTaskId;
    @ManyToOne
    @JoinColumn(name = "project_ID", nullable = false)
    private Project project;
    @ManyToOne
    @JoinColumn(name = "task_parentTask_ID", nullable = false)
    private Task parentTask;
    @OneToMany(mappedBy = "parentTask")
    private Collection<Task> subtasks;


    @PrePersist
    public void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.taskCreatedAt = now.minusNanos(now.getNano());
        this.taskUpdatedAt = this.taskCreatedAt;
    }

    @PreUpdate
    public void onUpdate() {
        LocalDateTime now = LocalDateTime.now();
        this.taskUpdatedAt = now.minusNanos(now.getNano());
    }

    public Task() {
    }

    public Task(String taskTitle, String taskDescription, Project project) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.project = project;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public LocalDateTime getTaskCreatedAt() {
        return taskCreatedAt;
    }

    public void setTaskCreatedAt(LocalDateTime taskCreatedAt) {
        this.taskCreatedAt = taskCreatedAt;
    }

    public LocalDateTime getTaskUpdatedAt() {
        return taskUpdatedAt;
    }

    public void setTaskUpdatedAt(LocalDateTime taskUpdatedAt) {
        this.taskUpdatedAt = taskUpdatedAt;
    }


    public Collection<ProjectHour> getProjectHoursByTaskId() {
        return projectHoursByTaskId;
    }

    public void setProjectHoursByTaskId(Collection<ProjectHour> projectHoursByTaskId) {
        this.projectHoursByTaskId = projectHoursByTaskId;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Task getTaskParent() {
        return parentTask;
    }

    public void setParentTask(Task parentTask) {
        this.parentTask = parentTask;
    }

    public Collection<Task> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(Collection<Task> subtasks) {
        this.subtasks = subtasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskId.equals(task.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId);
    }
}
