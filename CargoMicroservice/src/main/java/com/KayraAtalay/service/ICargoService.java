package com.KayraAtalay.service;

import com.KayraAtalay.dto.request.DtoCargoIU;
import com.KayraAtalay.dto.request.UpdateStatusRequest;
import com.KayraAtalay.dto.response.DtoCargo;
import java.util.List;

public interface ICargoService {

    //(Just User)
    DtoCargo saveCargo(DtoCargoIU request);

    //(Just Courier ve Admin)
    DtoCargo updateCargoStatus(UpdateStatusRequest request);

    //Just Courier
    DtoCargo deliverCargo(Long cargoId ,Integer deliveryCode);

    //Public
    DtoCargo findByTrackingNumber(String trackingNumber);

    //(Just User)
    List<DtoCargo> getCargosBySenderId(Long senderId);

}