package com.umityasincoban.amasia_fide.service;

import com.umityasincoban.amasia_fide.entity.Language;
import com.umityasincoban.amasia_fide.exception.LanguageNotFoundException;
import com.umityasincoban.amasia_fide.repository.LanguageRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LanguageServiceImpl implements LanguageService{
    private static final Logger logger = LoggerFactory.getLogger(LanguageServiceImpl.class);

    private final LanguageRepository languageRepository;

    @Override
    public Language getLanguageById(int id) {
        return languageRepository.findById(id).orElseThrow(LanguageNotFoundException::new);
    }

    @Override
    public Language getLanguageByKey(String key) {
        return languageRepository.getLanguageByKey(key).orElseThrow(LanguageNotFoundException::new);
    }

}