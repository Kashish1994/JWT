package com.wooplr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wooplr.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
