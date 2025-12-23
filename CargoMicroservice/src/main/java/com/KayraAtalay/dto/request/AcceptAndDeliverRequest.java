package com.KayraAtalay.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcceptAndDeliverRequest {
    private String trackingNumber;

    private Integer deliveryOrCreatedCode;
}
