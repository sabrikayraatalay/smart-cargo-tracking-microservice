package com.KayraAtalay.enums;

import lombok.Getter;

@Getter
public enum CargoStatus {
    CREATED,
    CANCELLED,
    RECEIVED,
    IN_TRANSIT,
    OUT_FOR_DELIVERY,
    DELIVERED,
    UNDELIVERABLE
}
