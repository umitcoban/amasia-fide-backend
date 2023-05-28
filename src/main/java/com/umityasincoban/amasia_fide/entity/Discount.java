package com.umityasincoban.amasia_fide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.Date;

@Table(name = "discounts")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "discount_id")
    private int discountId;

    @Column(nullable = false, unique = true, length = 36)
    @Size(min = 36, max = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String code;

    @Column(name = "start_date", nullable = false)
    @NotNull
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    @NotNull
    private Date endDate;

    @Column(name = "discount_rate", nullable = false)
    @Min(0)
    @Max(1)
    private double discountRate;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private Product productId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    @NotNull
    private Category categoryId;

    @Column(name= "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    @FutureOrPresent
    private ZonedDateTime createdAt;

    @Column(name= "updated_at", nullable = false)
    @UpdateTimestamp
    @FutureOrPresent
    private ZonedDateTime updatedAt;

}
