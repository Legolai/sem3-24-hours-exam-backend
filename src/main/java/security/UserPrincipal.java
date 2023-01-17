package security;

import entities.Account;
import entities.Role;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserPrincipal implements Principal {

  private final String email;
  private List<String> roles = new ArrayList<>();

  /* Create a UserPrincipal, given the Entity class User*/
  public UserPrincipal(Account account) {
    this.email = account.getAccountEmail();
    this.roles = account.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList());
  }

  public UserPrincipal(String email, String[] roles) {
    super();
    this.email = email;
    this.roles = Arrays.asList(roles);
  }

  @Override
  public String getName() {
    return email;
  }

  public boolean isUserInRole(String role) {
    return this.roles.contains(role);
  }
}