package com.tms.repositories;

import com.tms.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Criteria
 */

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final SessionFactory sessionFactory;

    /**
     * HQL не предназначен на добавление новых строк, за исключением вставки между таблицами
     * ИСПОЛЬЗОВАТЬ МЕТОД persist
     */
    public User saveUser(User userInput) {
        User user = null;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.createQuery("insert into User(firstName, lastName) select firstName2, lastName2 from UserBkp", User.class);
            transaction.commit();
        } catch (Exception e) {
            log.error("Error saving user", e);
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        return user;
    }

    public List<User> findAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            cq.select(root);
            Query<User> query = session.createQuery(cq);
            return query.getResultList();
        } catch (Exception e) {
            log.error("Error finding users", e);
        }
        return List.of();
    }

    public User getUserById(Integer id) {
        User user = null;
        try (Session session = sessionFactory.openSession()) {
            //Создаем Criteria Builder
            CriteriaBuilder cb = session.getCriteriaBuilder();

            //Создание CriteriaQuery
            CriteriaQuery<User> cq = cb.createQuery(User.class);

            //Указываем корень запроса
            Root<User> root = cq.from(User.class);

            //Добавление условий
            cq.where(cb.equal(root.get("id"), id));
            Query<User> query = session.createQuery(cq);
            return query.getSingleResult();
        } catch (Exception e) {
            log.error("Error while getting user", e);
        }
        return user;
    }

    public User updateUser(User userInput) {
        Transaction transaction = null;
        User userUpdated = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaUpdate<User> cq = cb.createCriteriaUpdate(User.class);
            Root<User> rootUpdate = cq.from(User.class);
            cq.set(rootUpdate.get("firstName"), userInput.getFirstName());
            cq.set(rootUpdate.get("lastName"), userInput.getLastName());
            cq.set(rootUpdate.get("age"), userInput.getAge());
            cq.set(rootUpdate.get("email"), userInput.getEmail());
            cq.set(rootUpdate.get("updated"), LocalDateTime.now());
            cq.set(rootUpdate.get("id"), userInput.getId());
            cq.where(cb.equal(rootUpdate.get("id"), userInput.getId()));
            transaction.begin();

            session.createMutationQuery(cq).executeUpdate();
            transaction.commit();

            CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
            Root<User> rootQuery = criteriaQuery.from(User.class);
            criteriaQuery.where(cb.equal(rootQuery.get("id"), userInput.getId()));
            Query<User> query = session.createQuery(criteriaQuery);
            userUpdated = query.getSingleResult();
        } catch (Exception e) {
            log.error("Error while updating user", e);
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        return userUpdated;
    }

    public void deleteUserById(Integer id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaDelete<User> cq = cb.createCriteriaDelete(User.class);
            Root<User> root = cq.from(User.class);
            cq.where(cb.equal(root.get("id"), id));
            MutationQuery mutationQuery = session.createMutationQuery(cq);
            mutationQuery.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            log.error("Error while deleting user", e);
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
    }
}
