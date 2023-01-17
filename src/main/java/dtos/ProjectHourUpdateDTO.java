package dtos;

import entities.Developer;
import entities.ProjectHour;
import entities.Task;

public class ProjectHourUpdateDTO {
    private double hoursSpendt;
    private String description;

    private Integer projectHourId;

    private final Integer developerId;
    private final Long taskId;

    public ProjectHourUpdateDTO(double hoursSpendt, String description, Integer projectHourId, Integer developerId, Long taskId) {
        this.hoursSpendt = hoursSpendt;
        this.description = description;
        this.projectHourId = projectHourId;
        this.developerId = developerId;
        this.taskId = taskId;
    }

    public ProjectHour toEntity(){
        Developer developer = new Developer();
        developer.setDeveloperId(developerId);
        Task task = new Task();
        task.setTaskId(taskId);
        ProjectHour projectHour = new ProjectHour(hoursSpendt, description,task, developer);
        projectHour.setProjecthourId(projectHourId);
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

    public Integer getProjectHourId() {
        return projectHourId;
    }

    public void setProjectHourId(Integer projectHourId) {
        this.projectHourId = projectHourId;
    }
}
