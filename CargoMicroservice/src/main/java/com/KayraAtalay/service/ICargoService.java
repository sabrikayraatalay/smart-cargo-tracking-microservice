package com.KayraAtalay.service;

import com.KayraAtalay.dto.request.AcceptAndDeliverRequest;
import com.KayraAtalay.dto.request.DtoCargoIU;
import com.KayraAtalay.dto.request.UpdateStatusRequest;
import com.KayraAtalay.dto.response.DtoCargo;
import java.util.List;

public interface ICargoService {

    //User
   public DtoCargo saveCargo(DtoCargoIU request);

   //User
   public DtoCargo cancelCargo(Long cargoId);

    //Admin
    public  DtoCargo updateCargoStatus(UpdateStatusRequest request);

    //Just admin
    public DtoCargo acceptCargo(AcceptAndDeliverRequest acceptAndDeliverRequest);

    //Just Courier
    public  DtoCargo deliverCargo(AcceptAndDeliverRequest acceptAndDeliverRequest);

    //Public
    public DtoCargo findByTrackingNumber(String trackingNumber);

    //(Just User)
    public List<DtoCargo> getCargosBySenderId();

}