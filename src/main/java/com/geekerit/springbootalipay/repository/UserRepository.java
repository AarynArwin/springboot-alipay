package com.geekerit.springbootalipay.repository;

import com.geekerit.springbootalipay.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Aaryn
 */
public interface UserRepository extends JpaRepository<User,Long> {
}
