package org.example.microsoftlists.repository;

import jakarta.transaction.Transactional;
import org.example.microsoftlists.model.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigRepository extends JpaRepository<Config, String> {
    @Query("SELECT c FROM Config c WHERE c.column.id = :columnId")
    List<Config> findAllByColumnId(String columnId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Config c WHERE c.column.id = :columnId")
    void deleteAllByColumnId(String columnId);
}
