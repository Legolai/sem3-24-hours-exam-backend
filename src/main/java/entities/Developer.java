package entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
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
    private Integer developerBillingPrHour;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_ID", nullable = false)
    private Account account;

    @OneToMany(mappedBy = "developer")
    private Set<ProjectHour> projectHours = new LinkedHashSet<>();

    public Integer getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(Integer developerId) {
        this.developerId = developerId;
    }

    public Integer getDeveloperBillingPrHour() {
        return developerBillingPrHour;
    }

    public void setDeveloperBillingPrHour(Integer developerBillingPrHour) {
        this.developerBillingPrHour = developerBillingPrHour;
    }





}
