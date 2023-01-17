package dtos;

import entities.Project;
import entities.ProjectHour;
import entities.Task;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TaskFullDetailedDTO {

    private Long taskId;
    private String taskTitle;

    private String taskDescription;

    private LocalDateTime taskCreatedAt;

    private LocalDateTime taskUpdatedAt;
    private List<ProjectHourDevDTO> projectHours;
    private Task parentTask;
    private List<TaskMiniDTO> subtasks;

    public TaskFullDetailedDTO(Task task) {
        this.taskId = task.getTaskId();
        this.taskTitle = task.getTaskTitle();
        this.taskDescription = task.getTaskDescription();
        this.taskCreatedAt = task.getTaskCreatedAt();
        this.taskUpdatedAt = task.getTaskUpdatedAt();
        this.projectHours = task.getProjectHours().stream().map(ProjectHourDevDTO::new).collect(Collectors.toList());
        this.parentTask = task.getTaskParent();
        this.subtasks = task.getSubtasks().stream().map(TaskMiniDTO::new).collect(Collectors.toList());
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

    public List<ProjectHourDevDTO> getProjectHours() {
        return projectHours;
    }

    public void setProjectHours(List<ProjectHourDevDTO> projectHours) {
        this.projectHours = projectHours;
    }

    public Task getParentTask() {
        return parentTask;
    }

    public void setParentTask(Task parentTask) {
        this.parentTask = parentTask;
    }

    public List<TaskMiniDTO> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<TaskMiniDTO> subtasks) {
        this.subtasks = subtasks;
    }

}
