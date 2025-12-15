package com.KayraAtalay.dto.response;

import com.KayraAtalay.enums.CargoStatus;
import com.KayraAtalay.shared.dto.DtoBase;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DtoCargo extends DtoBase {

    private String trackingNumber;

    private String receiverName;

    private CargoStatus status;

}
