package com.tms.repositories;

import com.tms.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final SessionFactory sessionFactory;

    public User saveUser(User userInput) {
        User user = null;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.persist(userInput);
            user = session.find(User.class, userInput.getId());
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
            user = session.find(User.class, id);
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
            transaction.begin();
            userUpdated = session.merge(userInput);
            transaction.commit();
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
            session.remove(session.find(User.class, id));
            transaction.commit();
        } catch (Exception e) {
            log.error("Error while deleting user", e);
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
    }
}
