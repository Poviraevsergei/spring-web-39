package com.tms.repositories;

import com.tms.model.Security;
import com.tms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityRepository extends JpaRepository<Security, Integer> {
    boolean existsByUsername(String username);
    Optional<Security> findByUsername(String username);
}
