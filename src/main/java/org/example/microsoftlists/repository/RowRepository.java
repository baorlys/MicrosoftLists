package org.example.microsoftlists.repository;

import jakarta.transaction.Transactional;
import org.example.microsoftlists.model.Row;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RowRepository extends JpaRepository<Row, String> {

    @Query("SELECT r FROM Row r WHERE r.list.id = :listId")
    List<Row> findAllByListId(String listId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Row c WHERE c.id = :rowId")
    void deleteById(String rowId);
}
