package dtos;

import entities.Developer;
import entities.ProjectHour;
import entities.Task;

public class ProjectHourCreateDTO {

    private double hoursSpendt;
    private String description;
    private Long taskId;
    private int developerId;

    public ProjectHourCreateDTO(double hoursSpendt, String description, Long taskId, int developerId) {
        this.hoursSpendt = hoursSpendt;
        this.description = description;
        this.taskId = taskId;
        this.developerId = developerId;
    }

    public ProjectHour toEntity() {
        Developer developer = new Developer();
        developer.setDeveloperId(developerId);
        Task task = new Task();
        task.setTaskId(taskId);
        ProjectHour projectHour = new ProjectHour(hoursSpendt, description, task, developer);
        return projectHour;
    }

    public double getHoursSpendt() {
        return hoursSpendt;
    }

    public void setHoursSpendt(double hoursSpendt) {
        this.hoursSpendt = hoursSpendt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public int getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(int developerId) {
        this.developerId = developerId;
    }
}
