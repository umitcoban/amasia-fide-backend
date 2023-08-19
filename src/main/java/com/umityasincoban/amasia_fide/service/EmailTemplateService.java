package com.umityasincoban.amasia_fide.service;

import com.umityasincoban.amasia_fide.entity.EmailTemplate;
import com.umityasincoban.amasia_fide.entity.Language;
import org.springframework.stereotype.Service;

@Service
public interface EmailTemplateService {
    EmailTemplate getEmailTemplateByNameAndLanguage(String name, Language language);
}
