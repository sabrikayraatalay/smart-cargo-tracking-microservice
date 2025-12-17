package com.KayraAtalay.enums;

import lombok.Getter;

@Getter
public enum CargoStatus {
    CREATED,
    RECEIVED,
    IN_TRANSIT,
    OUT_FOR_DELIVERY,
    DELIVERED,
    UNDELIVERABLE
}
