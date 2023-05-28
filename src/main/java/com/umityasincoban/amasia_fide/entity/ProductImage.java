package com.umityasincoban.amasia_fide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.URL;

import java.time.ZonedDateTime;

@Table(name = "product_images")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_image_id")
    private long id;

    @Column
    @URL
    @ColumnDefault("https://images.pexels.com/photos/4033324/pexels-photo-4033324.jpeg")
    private String url;

    @Column(name = "alt_text")
    private String altText;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private Product productId;

    @Column(name= "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    @FutureOrPresent
    private ZonedDateTime createdAt;

    @Column(name= "updated_at", nullable = false)
    @UpdateTimestamp
    @FutureOrPresent
    private ZonedDateTime updatedAt;

}
