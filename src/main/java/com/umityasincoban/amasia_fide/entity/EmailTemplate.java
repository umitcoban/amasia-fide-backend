package com.umityasincoban.amasia_fide.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "email_templates")
public class EmailTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_templates_email_template_id_seq")
    @SequenceGenerator(name = "email_templates_email_template_id_seq", sequenceName = "email_templates_email_template_id_seq")
    private long emailTemplateId;
    @Column(name = "name")
    private String name;
    @Column(name = "subject")
    private String subject;
    @Column(name = "body")
    private String body;
    @OneToOne(targetEntity = Language.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.PERSIST})
    @JoinColumn(name = "language_id", referencedColumnName = "language_id")
    private Language language;
}
