package dtos;

import entities.Project;
import entities.ProjectHour;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class InvoiceDTO {

    private Integer projectId;
    private String projectDescription;

    private Double totalHoursUsed;

    private List<InvoiceRow> records;

    private Double totalAmount;

    public class InvoiceRow {

        private Double hours;
        private Double developerBillingPrHour;
        private Double rowTotal;

        public InvoiceRow(Double hours, Double developerBillingPrHour) {
            this.hours = hours;
            this.developerBillingPrHour = developerBillingPrHour;
            this.rowTotal = hours * developerBillingPrHour;
        }

        public Double getHours() {
            return hours;
        }

        public Double getDeveloperBillingPrHour() {
            return developerBillingPrHour;
        }

        public Double getRowTotal() {
            return rowTotal;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            InvoiceRow that = (InvoiceRow) o;
            return Objects.equals(hours, that.hours) && Objects.equals(developerBillingPrHour, that.developerBillingPrHour) && Objects.equals(rowTotal, that.rowTotal);
        }

        @Override
        public int hashCode() {
            return Objects.hash(hours, developerBillingPrHour, rowTotal);
        }

        @Override
        public String toString() {
            return "InvoiceRow{" +
                    "hours=" + hours +
                    ", developerBillingPrHour=" + developerBillingPrHour +
                    ", rowTotal=" + rowTotal +
                    '}';
        }
    }


    public InvoiceDTO(Project project) {
        List<ProjectHour> projectHours = project.getDevelopers().stream().flatMap(developer -> developer.getProjectHours().stream()).collect(Collectors.toList());
        this.projectId = project.getProjectId();
        this.projectDescription = project.getProjectDescription();
        this.totalHoursUsed = projectHours.stream().reduce(0.0, (acc, r) -> acc + r.getProjecthourHoursSpendt(), Double::sum);
        this.records = projectHours.stream().map(r -> new InvoiceRow(r.getProjecthourHoursSpendt(), r.getDeveloper().getDeveloperBillingPrHour())).collect(Collectors.toList());
        this.totalAmount = records.stream().reduce(0.0, (acc, r) -> acc + r.getRowTotal(), Double::sum);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceDTO that = (InvoiceDTO) o;
        return Objects.equals(projectId, that.projectId) && Objects.equals(projectDescription, that.projectDescription) && Objects.equals(totalHoursUsed, that.totalHoursUsed) && Objects.equals(records, that.records) && Objects.equals(totalAmount, that.totalAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, projectDescription, totalHoursUsed, records, totalAmount);
    }

    @Override
    public String toString() {
        return "InvoiceDTO{" +
                "projectId=" + projectId +
                ", projectDescription='" + projectDescription + '\'' +
                ", totalHoursUsed=" + totalHoursUsed +
                ", records=" + records +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
