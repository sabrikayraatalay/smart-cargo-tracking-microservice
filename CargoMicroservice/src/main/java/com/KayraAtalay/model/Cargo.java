package com.KayraAtalay.model;

import com.KayraAtalay.enums.CargoStatus;
import com.KayraAtalay.shared.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cargos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cargo extends BaseEntity {

    @Column(name = "tracking_number", unique = true,nullable =false)
    private String trackingNumber;

    @Column(name = "delivery_code", nullable = false)
    private Integer deliveryCode;

    @Column(name = "created_code",  nullable = false)
    private Integer createdCode;

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "receiver_email")
    private String receiverEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CargoStatus status;

}
