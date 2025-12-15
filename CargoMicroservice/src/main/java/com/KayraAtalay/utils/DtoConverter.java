package com.KayraAtalay.utils;

import com.KayraAtalay.dto.response.DtoCargo;
import com.KayraAtalay.model.Cargo;
import lombok.Getter;

@Getter
public class DtoConverter {

    public static DtoCargo toDtoCargo(Cargo cargo) {
        DtoCargo dtoCargo = new DtoCargo();
        dtoCargo.setId(cargo.getId());
        dtoCargo.setReceiverName(cargo.getReceiverName());
        dtoCargo.setStatus(cargo.getStatus());
        dtoCargo.setTrackingNumber(cargo.getTrackingNumber());
        dtoCargo.setCreateTime(cargo.getCreateTime());

        return dtoCargo;
    }

}
