package com.nhnacademy.booklay.booklaycoupon.repository.coupon;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CouponJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public CouponJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveProductCoupons(Long couponId, int quantity) {
        jdbcTemplate.batchUpdate("insert into product_coupon(coupon_no, coupon_code) " +
                "values(?, ?)",
            new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setLong(1, couponId);
                    ps.setString(2, getCode());
                }

                @Override
                public int getBatchSize() {
                    return quantity;
                }
            });
    }
    public void saveOrderCoupons(Long couponId, int quantity) {
        jdbcTemplate.batchUpdate("insert into product_coupon(coupon_no, coupon_code) " +
                "values(?, ?)",
            new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setLong(1, couponId);
                    ps.setString(2, getCode());
                }

                @Override
                public int getBatchSize() {
                    return quantity;
                }
            });
    }

    private String getCode() {
        return UUID.randomUUID().toString().substring(0, 30);
    }

}
