package com.api.management.leave.leavemanagementapi.repository;

import com.api.management.leave.leavemanagementapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
