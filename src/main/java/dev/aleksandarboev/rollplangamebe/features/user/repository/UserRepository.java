package dev.aleksandarboev.rollplangamebe.features.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    @Query("""
            SELECT u FROM UserEntity u
            JOIN FETCH u.characters
            WHERE u.id = :userId
            """)
    Optional<UserEntity> findByIdAndFetchAllCharacters(@Param("userId") Long userId);
}
