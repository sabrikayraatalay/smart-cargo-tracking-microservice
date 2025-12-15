package com.KayraAtalay.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DtoCargoIU {

    @NotNull
    private String receiverName;

    private String receiverEmail;

    private String receiverAddress;

    private BigDecimal price;

}
