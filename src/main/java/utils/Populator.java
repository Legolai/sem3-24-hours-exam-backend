package utils;

import daos.AccountDao;
import daos.DeveloperDao;
import daos.ProjectDao;
import daos.TaskDao;
import dtos.AccountDTO;
import entities.*;
import security.errorhandling.AuthenticationException;
import services.AccountService;

import javax.persistence.EntityManagerFactory;

public class Populator {
    public static void populate() throws AuthenticationException {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        AccountDao accountDao = AccountDao.getInstance(emf);

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

        ProjectDao projectDao = ProjectDao.getInstance(emf);
//        AccountService accountService = AccountService.getInstance(emf);
//
//        AccountDTO accountDTO = accountService.getVerifiedAccount("superuser@email.com", "SuperCool123");
//        Account account = new Account();
//        account.setAccountId(accountDTO.getAccountId());
//        Project project1 = new Project("Project Steal the Moon", "See the movie despicable me", account);
//        Project project2 = new Project("Project take over the world", "Learn the cats master plan", account);
//        projectDao.create(project1);
//        projectDao.create(project2);

//        Account account2 = new Account("slave1", "developer1@email.com", "88 88 99 88", "Test123");
//        Account account3 = new Account("slave2", "developer2@email.com", "99 88 88 88", "Test123");
//        account2.addRole(new Role("developer"));
//        account3.addRole(new Role("developer"));
//
//        Developer developer1 = new Developer(100.0, account2);
//        Developer developer2 = new Developer(150.0, account3);
//
//        accountDao.create(account2);
//        accountDao.create(account3);
//
//        DeveloperDao developerDao = DeveloperDao.getInstance(emf);
//
//        developerDao.create(developer1);
//        developerDao.create(developer2);

//        Developer developer = developerDao.getById(1);
//
        Project project = projectDao.getById(1);
//        project.addDeveloper(developer);
//        developer.addProject(project);
//
//        projectDao.update(project);
//        developerDao.update(developer);


        Task task1 = new Task("Step 1, find people", "Find as many as possible.", project);
        Task task2 = new Task("Step 2, find materials", "Get Elon Musk onboard.", project);

        TaskDao taskDao = TaskDao.getInstance(emf);

        taskDao.create(task1);
        taskDao.create(task2);



    }

    public static void main(String[] args) throws AuthenticationException {
        populate();
    }
}
