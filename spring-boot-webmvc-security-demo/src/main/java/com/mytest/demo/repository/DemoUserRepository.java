package com.mytest.demo.repository;

import com.mytest.demo.entity.DemoUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoUserRepository extends JpaRepository<DemoUser, Long> {

    DemoUser findOneByUsername(String username);
}