package com.umityasincoban.amasia_fide.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_role_id_seq")
    @SequenceGenerator(name = "roles_role_id_seq", sequenceName = "roles_role_id_seq", allocationSize = 1)
    @Column(name = "role_id")
    private int role_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 50)
    private ERole role;
}
