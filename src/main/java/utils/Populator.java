package utils;

import daos.AccountDao;
import daos.ProjectDao;
import dtos.AccountDTO;
import entities.Account;
import entities.Project;
import entities.Role;
import security.errorhandling.AuthenticationException;
import services.AccountService;

import javax.persistence.EntityManagerFactory;

public class Populator {
    public static void populate() throws AuthenticationException {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
//        AccountDao accountDao = AccountDao.getInstance(emf);

//        Role adminRole = new Role("admin");
//        adminRole.setRolesDescription("This grants an account admin permissions.");
//        Role developerRole = new Role("developer");
//        developerRole.setRolesDescription("This grants an account developer permissions.");
//        accountDao.executeInsideTransaction((em) -> {
//            em.persist(adminRole);
//            em.persist(developerRole);
//        });
//        Account account = new Account("superUser", "superuser@email.com", "88 88 88 88", "SuperCool123");
//        account.addRole(adminRole);
//        accountDao.create(account);

//        ProjectDao projectDao = ProjectDao.getInstance(emf);
//        AccountService accountService = AccountService.getInstance(emf);
//
//        AccountDTO accountDTO = accountService.getVerifiedAccount("superuser@email.com", "SuperCool123");
//        Account account = new Account();
//        account.setAccountId(accountDTO.getAccountId());
//        Project project1 = new Project("Project Steal the Moon", "See the movie despicable me", account);
//        Project project2 = new Project("Project take over the world", "Learn the cats master plan", account);
//        projectDao.create(project1);
//        projectDao.create(project2);



    }

    public static void main(String[] args) throws AuthenticationException {
        populate();
    }
}
