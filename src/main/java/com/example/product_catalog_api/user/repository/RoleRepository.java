package com.example.product_catalog_api.user.repository;

import com.example.product_catalog_api.user.entity.Role;
import com.example.product_catalog_api.user.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(RoleEnum role);
}
