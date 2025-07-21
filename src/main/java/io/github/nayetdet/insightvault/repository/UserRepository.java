package io.github.nayetdet.insightvault.repository;

import io.github.nayetdet.insightvault.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);

    @Transactional
    @Modifying
    @Query("""
        UPDATE User u
        SET u.password = :password
        WHERE u.id = :id
        """)
    void resetPassword(Long id, String password);

}
