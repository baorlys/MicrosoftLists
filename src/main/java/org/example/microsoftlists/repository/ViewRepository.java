package org.example.microsoftlists.repository;

import org.example.microsoftlists.model.view.View;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewRepository extends JpaRepository<View, String> {
}
