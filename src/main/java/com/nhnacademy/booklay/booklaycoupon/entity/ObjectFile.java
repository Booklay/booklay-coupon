package com.nhnacademy.booklay.booklaycoupon.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "object_file")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ObjectFile {

    @Id
    @Column(name = "file_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String fileAddress;

    @Column(name = "file_name")
    private String fileName;

    @Builder
    public ObjectFile(String fileAddress, String fileName) {
        this.fileAddress = fileAddress;
        this.fileName = fileName;
    }
}
