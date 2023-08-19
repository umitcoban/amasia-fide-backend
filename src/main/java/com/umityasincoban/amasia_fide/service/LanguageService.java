package com.umityasincoban.amasia_fide.service;

import com.umityasincoban.amasia_fide.entity.Language;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public interface LanguageService {
    Language getLanguageById(int id);
    Language getLanguageByKey(String key);
}
