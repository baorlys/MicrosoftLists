package org.example.microsoftlists.repository;

import org.example.microsoftlists.model.MicrosoftList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MicrosoftListRepository extends JpaRepository<MicrosoftList, String> {
}
