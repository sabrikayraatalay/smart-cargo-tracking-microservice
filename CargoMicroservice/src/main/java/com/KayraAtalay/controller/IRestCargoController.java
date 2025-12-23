package com.KayraAtalay.controller;

import com.KayraAtalay.dto.request.AcceptAndDeliverRequest;
import com.KayraAtalay.dto.request.DtoCargoIU;
import com.KayraAtalay.dto.request.UpdateStatusRequest;
import com.KayraAtalay.dto.response.DtoCargo;
import com.KayraAtalay.shared.response.RootEntity;


import java.util.List;

public interface IRestCargoController {

    public RootEntity<DtoCargo> saveCargo(DtoCargoIU request);

    public RootEntity<DtoCargo> cancelCargo(Long cargoId);

    public RootEntity<DtoCargo> updateCargoStatus(UpdateStatusRequest request);

    public RootEntity<DtoCargo> acceptCargo(AcceptAndDeliverRequest acceptAndDeliverRequest);

    public RootEntity<DtoCargo> deliverCargo(AcceptAndDeliverRequest acceptAndDeliverRequest);

    public RootEntity<DtoCargo> findByTrackingNumber(String trackingNumber);

    public RootEntity<List<DtoCargo>> findCargosBySenderId();

}
