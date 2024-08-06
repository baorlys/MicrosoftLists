package org.example.microsoftlists.repository;

import jakarta.transaction.Transactional;
import org.example.microsoftlists.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<Template, String> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Template t WHERE t.id = :templateId")
    void deleteById(String templateId);
}
