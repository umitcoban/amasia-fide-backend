package com.umityasincoban.amasia_fide.service;

import com.umityasincoban.amasia_fide.entity.EmailTemplate;
import com.umityasincoban.amasia_fide.entity.Language;
import com.umityasincoban.amasia_fide.exception.EmailTemplateNotFoundException;
import com.umityasincoban.amasia_fide.repository.EmailTemplateRepository;
import com.umityasincoban.amasia_fide.service.EmailTemplateService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailTemplateImpl implements EmailTemplateService {

    private static final Logger logger = LoggerFactory.getLogger(EmailTemplateImpl.class);
    private final EmailTemplateRepository emailTemplateRepository;

    @Override
    public EmailTemplate getEmailTemplateByNameAndLanguage(String name, Language language) {
        return emailTemplateRepository.findEmailTemplateByNameAndLanguage(name, language).orElseThrow(EmailTemplateNotFoundException::new);
    }
}
