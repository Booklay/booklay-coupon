package com.nhnacademy.booklay.booklaycoupon.repository.coupon;

import com.nhnacademy.booklay.booklaycoupon.util.CodeUtils;
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
                    ps.setString(2, CodeUtils.getProductCouponCode());
                }

                @Override
                public int getBatchSize() {
                    return quantity;
                }
            });
    }
    public void saveOrderCoupons(Long couponId, int quantity) {
        jdbcTemplate.batchUpdate("insert into order_coupon(coupon_no, coupon_code, is_used) " +
                "values(?, ?, ?)",
            new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setLong(1, couponId);
                    ps.setString(2, CodeUtils.getOrderCouponCode());
                    ps.setInt(3, 0);
                }

                @Override
                public int getBatchSize() {
                    return quantity;
                }
            });
    }

}
