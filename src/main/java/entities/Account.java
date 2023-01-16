package entities;

import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQuery(name = "account.deleteAllRows", query = "DELETE from Account ")
@Table(name = "Accounts")
public class Account {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "account_ID")
    private Integer accountId;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "account_email")
    private String accountEmail;

    @Column(name = "account_phone")
    private String accountPhone;

    @ManyToMany
    @JoinTable(name = "accounts_has_roles",
            joinColumns = @JoinColumn(name = "account_ID"),
            inverseJoinColumns = @JoinColumn(name = "role_ID"))
    private Set<Role> roles  = new LinkedHashSet<>();;

    @Column(name = "account_createdAt")
    private LocalDateTime accountCreatedAt;

    @Column(name = "account_updatedAt")
    private LocalDateTime accountUpdatedAt;

    @Column(name = "accounts_password")
    private String accountsPassword;


    @PrePersist
    public void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.accountCreatedAt = now.minusNanos(now.getNano());
        this.accountUpdatedAt = this.accountCreatedAt;
    }

    @PreUpdate
    public void onUpdate() {
        LocalDateTime now = LocalDateTime.now();
        this.accountUpdatedAt = now.minusNanos(now.getNano());
    }


    public Account() {
    }

    public Account(String accountName, String accountEmail, String accountPhone, String accountsPassword) {
        this.accountName = accountName;
        this.accountEmail = accountEmail;
        this.accountPhone = accountPhone;
        this.accountsPassword = BCrypt.hashpw(accountsPassword, BCrypt.gensalt());
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public String getAccountPhone() {
        return accountPhone;
    }

    public void setAccountPhone(String accountPhone) {
        this.accountPhone = accountPhone;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRole(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        if(roles.contains(role)) return;
        roles.add(role);
    }

    public LocalDateTime getAccountCreatedAt() {
        return accountCreatedAt;
    }

    public void setAccountCreatedAt(LocalDateTime accountCreatedAt) {
        this.accountCreatedAt = accountCreatedAt;
    }

    public LocalDateTime getAccountUpdatedAt() {
        return accountUpdatedAt;
    }

    public void setAccountUpdatedAt(LocalDateTime accountUpdatedAt) {
        this.accountUpdatedAt = accountUpdatedAt;
    }

    public String getAccountsPassword() {
        return accountsPassword;
    }

    public void setAccountsPassword(String accountsPassword) {
        this.accountsPassword = accountsPassword;
    }

    public boolean verifyPassword(String password) {
        return BCrypt.checkpw(password, this.accountsPassword);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountId.equals(account.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId);
    }
}
