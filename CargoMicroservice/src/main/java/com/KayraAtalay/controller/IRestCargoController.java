package com.KayraAtalay.controller;

import com.KayraAtalay.dto.request.DtoCargoIU;
import com.KayraAtalay.dto.request.UpdateStatusRequest;
import com.KayraAtalay.dto.response.DtoCargo;
import com.KayraAtalay.shared.response.RootEntity;


import java.util.List;

public interface IRestCargoController {

    public RootEntity<DtoCargo> saveCargo(DtoCargoIU request);

    public RootEntity<DtoCargo> updateCargoStatus(UpdateStatusRequest request);

    public RootEntity<DtoCargo> findByTrackingNumber(String trackingNumber);

    public RootEntity<List<DtoCargo>> findCargosBySenderId(Long userId);

}
