package com.tms.repositories;

import com.tms.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * HQL = Hibernate Query Language
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

    //HQL
    public List<User> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.getResultList();
        } catch (Exception e) {
            log.error("Error finding users", e);
        }
        return List.of();
    }

    public User getUserById(Integer id) {
        User user = null;
        try (Session session = sessionFactory.openSession()) {
            Query<User> userQuery = session.createQuery("FROM User WHERE id = :id", User.class);
            userQuery.setParameter("id", id);
            user = userQuery.getSingleResult();
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
            MutationQuery mutationQuery = session.createMutationQuery("update User set firstName = :firstName, lastName = :lastName, age=:age, email=:email, updated=:updated where id = :id");
            mutationQuery.setParameter("firstName", userInput.getFirstName());
            mutationQuery.setParameter("lastName", userInput.getLastName());
            mutationQuery.setParameter("age", userInput.getAge());
            mutationQuery.setParameter("email", userInput.getEmail());
            mutationQuery.setParameter("updated", LocalDateTime.now());
            mutationQuery.setParameter("id", userInput.getId());
            transaction.begin();
            mutationQuery.executeUpdate();
            transaction.commit();
            Query<User> userQuery = session.createQuery("FROM User WHERE id = :id", User.class);
            userQuery.setParameter("id", userInput.getId());
            userUpdated = userQuery.getSingleResult();
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
            MutationQuery userQuery = session.createMutationQuery("DELETE FROM User WHERE id = :id");
            userQuery.setParameter("id", id);
            userQuery.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            log.error("Error while deleting user", e);
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
    }
}
