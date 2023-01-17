package services;

import daos.AccountDao;
import dtos.AccountDTO;
import entities.Account;
import security.errorhandling.AuthenticationException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class AccountService {

    private static EntityManagerFactory emf;
    private static AccountService instance;

    private static AccountDao accountDao;

    private AccountService() {
    }

    public static AccountService getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            accountDao = AccountDao.getInstance(emf);
            instance = new AccountService();
        }
        return instance;
    }

    public AccountDTO getVerifiedAccount(String email, String password) throws AuthenticationException {
        List<Account> accounts = accountDao.executeWithClose((em) -> {
            TypedQuery<Account> query = em.createQuery("SELECT a FROM Account a WHERE a.accountEmail = :email", Account.class);
            query.setParameter("email", email);
            return query.getResultList();
        });

        if(accounts.isEmpty() || !accounts.get(0).verifyPassword(password)){
            throw new AuthenticationException("Invalid email or password");
        }


        return new AccountDTO(accounts.get(0));
    }
}
