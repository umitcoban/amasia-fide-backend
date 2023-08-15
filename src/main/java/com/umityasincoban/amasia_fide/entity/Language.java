package com.umityasincoban.amasia_fide.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "languages", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name"),
        @UniqueConstraint(columnNames = "key")
})
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "languages_language_id_seq")
    @SequenceGenerator(name = "languages_language_id_seq", sequenceName = "languages_language_id_seq", allocationSize = 1)
    @Column(name = "language_id")
    private int languageId;

    @Column(name = "name")
    private String name;

    @Column(name = "key")
    private String key;
}
