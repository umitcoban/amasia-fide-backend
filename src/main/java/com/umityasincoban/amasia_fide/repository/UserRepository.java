package com.umityasincoban.amasia_fide.repository;

import com.umityasincoban.amasia_fide.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUserId(long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "call public.sp_activate_use_by_id(:id, :status)", nativeQuery = true)
    void activateUserById(@Param("id") long id, @Param("status") boolean status);
}
