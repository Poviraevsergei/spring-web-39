package com.tms.repositories;

import com.tms.model.Security;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;


@Slf4j
@RequiredArgsConstructor
@Repository
public class SecurityRepository {
    private final SessionFactory sessionFactory;

    public void saveSecurity(Security securityInput) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.persist(securityInput);
            session.find(Security.class, securityInput.getId());
            transaction.commit();
        } catch (Exception e) {
            log.error("Save security with transaction", e);
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public Security getSecurityById(Integer id) {
        Security security = null;
        try (Session session = sessionFactory.openSession()) {
            security = session.find(Security.class, id);
        } catch (Exception e) {
            log.error("Error while getting user", e);
        }
        return security;
    }
}
