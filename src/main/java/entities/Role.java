package entities;

import javax.persistence.*;
import java.util.*;

@Entity
@NamedQuery(name = "role.deleteAllRows", query = "DELETE from Role ")
@Table(name = "Roles")
public class Role {

    @Id
    @Column(name = "role_name")
    private String roleName;

    @Column(name = "roles_description")
    private String rolesDescription;

    @ManyToMany(mappedBy = "roles")
    private Set<Account> accounts = new LinkedHashSet<>();


    public Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRolesDescription() {
        return rolesDescription;
    }

    public void setRolesDescription(String rolesDescription) {
        this.rolesDescription = rolesDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return roleName.equals(role.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleName);
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
}
