package com.gjevents.usermanagementservice.repository;

import com.gjevents.usermanagementservice.model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Administrator, Long> {
    Administrator findAdministratorByEmail(String email);
}