package com.KayraAtalay.controller.impl;

import com.KayraAtalay.controller.IRestCargoController;
import com.KayraAtalay.dto.request.DtoCargoIU;
import com.KayraAtalay.dto.request.UpdateStatusRequest;
import com.KayraAtalay.dto.response.DtoCargo;
import com.KayraAtalay.service.ICargoService;
import com.KayraAtalay.shared.response.RestBaseController;
import com.KayraAtalay.shared.response.RootEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.KayraAtalay.config.RestApis.*;

@RestController
@RequestMapping(path = CARGO)
@RequiredArgsConstructor
public class RestCargoControllerImpl extends RestBaseController implements IRestCargoController {
                private final ICargoService cargoService;

    @Override
    public RootEntity<DtoCargo> saveCargo(DtoCargoIU request) {
        return null;
    }

    @Override
    public RootEntity<DtoCargo> updateCargoStatus(UpdateStatusRequest request) {
        return null;
    }

    @Override
    public RootEntity<DtoCargo> findByTrackingNumber(String trackingNumber) {
        return null;
    }

    @Override
    public RootEntity<List<DtoCargo>> findCargosBySenderId(Long userId) {
        return null;
    }
}
