package dtos;

import entities.ProjectHour;

public class ProjectHourDevDTO {

    private Integer projecthourId;
    private Double hoursSpendt;
    private String description;

    private int accountId;

    private String accountName;

    public ProjectHourDevDTO(ProjectHour projectHour) {
        this.projecthourId = projectHour.getProjecthourId();
        this.hoursSpendt = projectHour.getProjecthourHoursSpendt();
        this.description = projectHour.getProjecthourDescription();
        this.accountId = projectHour.getDeveloper().getAccount().getAccountId();
        this.accountName = projectHour.getDeveloper().getAccount().getAccountName();
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

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
