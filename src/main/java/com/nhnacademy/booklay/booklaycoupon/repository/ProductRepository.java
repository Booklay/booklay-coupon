package com.nhnacademy.booklay.booklaycoupon.repository;

import com.nhnacademy.booklay.booklaycoupon.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
