package com.nhnacademy.booklay.booklaycoupon.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name = "delivery_destination")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class DeliveryDestination {

    @Id
    @Column(name = "delivery_destination_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @Column
    private String name;

    @Column(name = "zip_code")
    private String zipCode;

    @Column
    private String address;

    @Column(name = "is_default_destination")
    private Boolean isDefaultDestination;

    @Builder
    public DeliveryDestination(Member member, String name, String zipCode, String address, Boolean isDefaultDestination) {
        this.member = member;
        this.name = name;
        this.zipCode = zipCode;
        this.address = address;
        this.isDefaultDestination = isDefaultDestination;
    }
}
