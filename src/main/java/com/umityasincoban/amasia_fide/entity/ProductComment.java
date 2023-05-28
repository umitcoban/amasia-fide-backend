package com.umityasincoban.amasia_fide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Table(name = "product_comments")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_commnet_id")
    private long productCommentId;

    @Column(name = "comment", length = 300, nullable = false)
    @Size(max = 300)
    private String Comment;

    @Column(name = "rate", nullable = false)
    @Min(1)
    @Max(11)
    private int rate;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User userId;

    @OneToOne( fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
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
