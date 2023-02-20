package com.nhnacademy.booklay.booklaycoupon.repository.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponUsingDto;
import com.nhnacademy.booklay.booklaycoupon.util.CodeUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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

    public void useProductCoupons(List<CouponUsingDto> productCouponList, Long memberNo) {
        jdbcTemplate.batchUpdate("update product_coupon set order_product_no = ? " +
                        "where product_coupn_no = ? and member_no = ?",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, productCouponList.get(i).getSpecifiedCouponNo());
                        ps.setLong(2, productCouponList.get(i).getUsedTargetNo());
                        ps.setLong(3, memberNo);
                    }

                    @Override
                    public int getBatchSize() {
                        return productCouponList.size();
                    }
                });
    }

    public void refundProductCoupons(List<Long> orderProductNoList, Long memberNo) {
        jdbcTemplate.batchUpdate("update product_coupon set order_product_no = null " +
                        "where order_product_no = ? and member_no = ?",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, orderProductNoList.get(i));
                        ps.setLong(2, memberNo);
                    }

                    @Override
                    public int getBatchSize() {
                        return orderProductNoList.size();
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


    public void useOrderCoupons(List<Long> usedCouponNoList, Long orderNo, Long memberNo) {
        jdbcTemplate.batchUpdate("update order_coupon set is_used = true, order_no = ? " +
                        "where order_coupon_no in ? and member_no = ?",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, orderNo);
                        ps.setArray(2, ps.getConnection().createArrayOf("BIGINT", usedCouponNoList.toArray()));
                        ps.setLong(3, memberNo);
                    }

                    @Override
                    public int getBatchSize() {
                        return 1;
                    }
                });
    }

    public void refundOrderCoupons(Long orderNo, Long memberNo) {
        jdbcTemplate.batchUpdate("update order_coupon set is_used = false, order_no = null " +
                        "where order_no = ? and member_no = ?",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, orderNo);
                        ps.setLong(2, memberNo);
                    }

                    @Override
                    public int getBatchSize() {
                        return 1;
                    }
                });
    }
}
