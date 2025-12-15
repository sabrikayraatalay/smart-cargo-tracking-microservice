package com.KayraAtalay.shared.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMessage implements Serializable {
    private String trackingNumber;
    private String receiverEmail;
    private String receiverName;
    private Integer deliveryCode;
    private String messageType;
}