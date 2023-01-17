package com.nhnacademy.booklay.booklaycoupon.dto.secrets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class DatasourceInfo {

    private final String username;

    private final String password;

    private final String dbUrl;

}
