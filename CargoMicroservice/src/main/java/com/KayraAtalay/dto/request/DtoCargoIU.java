package com.KayraAtalay.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DtoCargoIU {

    @NotNull
    private String receiverName;

    @NotNull
    @Email
    private String receiverEmail;

    @NotNull
    private String deliveryAddress;

}
