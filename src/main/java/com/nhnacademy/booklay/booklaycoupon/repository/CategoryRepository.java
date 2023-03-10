package com.nhnacademy.booklay.booklaycoupon.repository;


import com.nhnacademy.booklay.booklaycoupon.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * javadoc. 카테고리 JPA 리포지토리
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
    <T> Page<T> findAllBy(Pageable pageable, Class<T> type);
}
