package com.umityasincoban.amasia_fide.repository;

import com.umityasincoban.amasia_fide.entity.EmailTemplate;
import com.umityasincoban.amasia_fide.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {
    Optional<EmailTemplate> findEmailTemplateByNameAndLanguage(String name, Language language);
}
