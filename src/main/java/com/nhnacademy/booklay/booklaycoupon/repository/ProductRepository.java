package com.nhnacademy.booklay.booklaycoupon.repository;

import com.nhnacademy.booklay.booklaycoupon.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
