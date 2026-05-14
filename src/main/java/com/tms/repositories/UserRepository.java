package com.tms.repositories;

import com.tms.model.User;
import com.tms.model.dto.UserDTO;
import com.tms.model.dto.UserUpdateRequestDTO;
import com.tms.util.SqlQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManagerFactory entityManagerFactory;
    private final JdbcTemplate jdbcTemplate;

    public User saveUser(User userInput) {
        User user = null;
        EntityTransaction entityTransaction = null;
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.persist(userInput);
            user = entityManager.find(User.class, userInput.getId());
            entityTransaction.commit();
        } catch (Exception e) {
            log.error("Error saving user", e);
            if (entityTransaction != null && entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
        return user;
    }

    public List<UserDTO> findAll() {
        return jdbcTemplate.query(SqlQueries.SQL_SELECT_ALL_USERS, new BeanPropertyRowMapper<>(UserDTO.class));
    }

    public User getUserById(Integer id) {
        User user = null;
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            user = entityManager.find(User.class, id);
        } catch (Exception e) {
            log.error("Error while getting user", e);
        }
        return user;
    }

    public boolean updateUser(UserUpdateRequestDTO userInput) {
        return jdbcTemplate.update(SqlQueries.SQL_UPDATE_USER_BY_ID,
                userInput.getFirstName(),
                userInput.getLastName(),
                userInput.getAge(),
                userInput.getEmail(),
                userInput.getId()) > 0;
    }

    public boolean deleteUserById(Integer id) {
        return jdbcTemplate.update(SqlQueries.SQL_DELETE_USER_BY_ID, id) > 0;
    }
}

//TODO: рассказать EntityManager cache


















