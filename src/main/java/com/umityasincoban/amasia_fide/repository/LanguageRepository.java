package com.umityasincoban.amasia_fide.repository;

import com.umityasincoban.amasia_fide.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {

    Optional<Language> getLanguageByKey(String key);

}
