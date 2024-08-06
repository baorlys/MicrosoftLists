package org.example.microsoftlists.repository;

import jakarta.transaction.Transactional;
import org.example.microsoftlists.model.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColumnRepository extends JpaRepository<Column, String> {
    @Query("SELECT c FROM Column c WHERE c.list.id = :listId")
    List<Column> findAllByListId(String listId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Column c WHERE c.id = :columnId")
    void deleteById(String columnId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Column c WHERE c.template.id = :templateId")
    void deleteAllByTemplateId(String templateId);
}
