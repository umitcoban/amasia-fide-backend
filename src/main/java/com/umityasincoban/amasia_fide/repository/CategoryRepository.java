package com.umityasincoban.amasia_fide.repository;

import com.umityasincoban.amasia_fide.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
