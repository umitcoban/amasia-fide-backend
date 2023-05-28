package com.umityasincoban.amasia_fide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.ZonedDateTime;

@Table(name = "addresses")
@Entity
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id")
    private long addressId;

    @Column(length = 90)
    @NotBlank
    private String name;

    @Column
    @NotBlank
    private String line;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id", nullable = false, referencedColumnName = "city_id")
    private City cityId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "district_id", nullable = false, referencedColumnName = "district_id")
    private District districtId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false ,referencedColumnName = "user_id")
    private User userId;

    @Column(name= "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    @FutureOrPresent
    private ZonedDateTime createdAt;

    @Column(name= "updated_at", nullable = false)
    @UpdateTimestamp
    @FutureOrPresent
    private ZonedDateTime updatedAt;

}
