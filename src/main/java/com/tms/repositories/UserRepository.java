package com.tms.repositories;

import com.tms.model.User;
import com.tms.model.dto.UserDTO;
import com.tms.model.dto.UserUpdateRequestDTO;
import com.tms.util.SqlQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User saveUser(User userInput) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SqlQueries.SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userInput.getFirstName());
            ps.setString(2, userInput.getLastName());
            ps.setString(3, userInput.getEmail());
            ps.setInt(4, userInput.getAge());
            ps.setTimestamp(5, Timestamp.valueOf(userInput.getCreated()));
            ps.setTimestamp(6, Timestamp.valueOf(userInput.getUpdated()));
            return ps;
        }, keyHolder);

        userInput.setId((int) keyHolder.getKeys().get("id"));
        return userInput;
    }

    public List<UserDTO> findAll() {
        return jdbcTemplate.query(SqlQueries.SQL_SELECT_ALL_USERS, new BeanPropertyRowMapper<>(UserDTO.class));
    }

    public UserDTO getUserById(Integer id) {
        return jdbcTemplate.queryForObject(SqlQueries.SQL_SELECT_USER_BY_ID, new BeanPropertyRowMapper<>(UserDTO.class), id);
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


















