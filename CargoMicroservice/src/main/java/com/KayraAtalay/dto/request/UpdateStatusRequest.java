package com.KayraAtalay.dto.request;

import com.KayraAtalay.enums.CargoStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusRequest {

    private String trackingNumber;

    private Integer createdCode;

    private CargoStatus cargoStatus;

}
