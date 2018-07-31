package com.mytest.demo.repository;

import com.mytest.demo.entity.DemoRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoRoleRepository extends JpaRepository<DemoRole, Long> {
}