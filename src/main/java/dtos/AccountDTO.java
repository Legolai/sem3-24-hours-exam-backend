package dtos;

import entities.Account;
import entities.Role;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private Integer accountId;
    private String accountName;

    private String accountEmail;
    private String accountPhone;

    private List<String> roles = new ArrayList<>();

    public AccountDTO(Integer accountId, String accountName, String accountEmail, String accountPhone, List<String> roles) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.accountEmail = accountEmail;
        this.accountPhone = accountPhone;
        this.roles = roles;
    }

    public AccountDTO(Account account) {
        this.accountId = account.getAccountId();
        this.accountName = account.getAccountName();
        this.accountEmail = account.getAccountEmail();
        this.accountPhone = account.getAccountPhone();
        this.roles = account.getRoles().stream().map(role -> role.getRoleName()).collect(Collectors.toList());
    }


    public static List<AccountDTO> listToDto(List<Account> accounts) {
        return accounts.stream().map(AccountDTO::new).collect(Collectors.toList());
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
