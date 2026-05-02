package com.revobank.accounts.repositories.accounts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    @Modifying
    @Query("update AccountEntity acc SET acc.status = :status, acc.modified = :modified WHERE acc.id = :id")
    int updateStatus(@Param("id") Long id, @Param("modified") LocalDateTime modified, @Param("status") Boolean status);

    Optional<AccountEntity> findByIdAndStatusIsTrue(Long id);
}
