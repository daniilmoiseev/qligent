package com.lottrading.ltt.repo;

import com.lottrading.ltt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
