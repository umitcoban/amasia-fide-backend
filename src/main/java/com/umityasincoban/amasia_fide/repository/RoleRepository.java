package com.umityasincoban.amasia_fide.repository;

import com.umityasincoban.amasia_fide.entity.ERole;
import com.umityasincoban.amasia_fide.entity.Role;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("select r from Role r where r.role = :roleName")
    Optional<Role> findRoleByRoleName(@Param("roleName") ERole roleName);

}
