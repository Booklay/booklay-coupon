package com.nhnacademy.booklay.booklaycoupon.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "cart")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

  @EmbeddedId
  private Pk pk;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_no")
  @MapsId("memberId")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_no")
  @MapsId("productId")
  private Product product;


  @Embeddable
  @EqualsAndHashCode
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Pk implements Serializable {

    @Column(name = "member_no")
    private Long memberId;

    @Column(name = "product_no")
    private Long productId;
  }

  @Builder
  public Cart(Pk pk, Member member, Product product) {
    this.pk = pk;
    this.member = member;
    this.product = product;
  }
}
