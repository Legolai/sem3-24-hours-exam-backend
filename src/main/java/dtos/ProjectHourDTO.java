package dtos;

import entities.Account;
import entities.ProjectHour;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectHourDTO {

    private Integer projecthourId;
    private Double hoursSpendt;
    private String description;

    private long taskId;
    private int developerId;

    public ProjectHourDTO(ProjectHour projectHour) {
        this.projecthourId = projectHour.getProjecthourId();
        this.hoursSpendt = projectHour.getProjecthourHoursSpendt();
        this.description = projectHour.getProjecthourDescription();
        this.taskId = projectHour.getTask().getTaskId();
        this.developerId = projectHour.getDeveloper().getDeveloperId();
    }

    public static List<ProjectHourDTO> listToDto(List<ProjectHour> entities) {
        return entities.stream().map(ProjectHourDTO::new).collect(Collectors.toList());
    }
    public Integer getProjecthourId() {
        return projecthourId;
    }

    public void setProjecthourId(Integer projecthourId) {
        this.projecthourId = projecthourId;
    }

    public Double getHoursSpendt() {
        return hoursSpendt;
    }

    public void setHoursSpendt(Double hoursSpendt) {
        this.hoursSpendt = hoursSpendt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public int getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(int developerId) {
        this.developerId = developerId;
    }
}
