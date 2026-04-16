package com.tms.util;

public interface SqlQueries {
    String SQL_INSERT_USER = "INSERT INTO users (id, first_name, last_name, email, age, created, updated) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)";
    String SQL_INSERT_SECURITY = "INSERT INTO security (id, username, password, role, user_id, created, updated) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)";
}
