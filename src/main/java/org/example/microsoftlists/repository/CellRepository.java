package org.example.microsoftlists.repository;

import jakarta.transaction.Transactional;
import org.example.microsoftlists.model.Cell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CellRepository extends JpaRepository<Cell, String> {
    @Query("DELETE FROM Cell c WHERE c.column.id = :columnId")
    void deleteCellsByColumnId(String columnId);
    @Modifying
    @Transactional
    @Query("DELETE FROM Cell c WHERE c.row.id = :rowId")
    void deleteByRowId(String rowId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Cell c WHERE c.column.id = :columnId")
    void deleteAllByColumnId(String columnId);
}
