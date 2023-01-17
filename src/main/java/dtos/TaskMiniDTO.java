package dtos;

import entities.Project;
import entities.ProjectHour;
import entities.Task;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TaskMiniDTO {
    private Long taskId;

    private String taskTitle;

    private String taskDescription;
    private List<TaskMiniDTO> subtasks;

    public TaskMiniDTO(Task task) {
        this.taskId = task.getTaskId();
        this.taskTitle = task.getTaskTitle();
        this.taskDescription = task.getTaskDescription();
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

    public List<TaskMiniDTO> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<TaskMiniDTO> subtasks) {
        this.subtasks = subtasks;
    }
}
