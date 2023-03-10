package daos;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface IDao<T, K> {

    EntityManager getEntityManager();

    default T create(T entity) {
        executeInsideTransaction((em) -> em.persist(entity));
        return entity;
    }

    T getById(K key);

    List<T> getAll();

    default void update(T entity) {
        executeInsideTransaction((em) -> em.merge(entity));
    }

    void deleteById(K key);

    default <R> R executeWithClose(Function<EntityManager, R> action) {
        EntityManager em = getEntityManager();
        R result = action.apply(em);
        em.close();
        return result;
    }

    default void executeInsideTransaction(Consumer<EntityManager> action) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            action.accept(em);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }
}
