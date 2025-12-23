package com.KayraAtalay.controller.impl;

import com.KayraAtalay.controller.IRestCargoController;
import com.KayraAtalay.dto.request.AcceptAndDeliverRequest;
import com.KayraAtalay.dto.request.DtoCargoIU;
import com.KayraAtalay.dto.request.UpdateStatusRequest;
import com.KayraAtalay.dto.response.DtoCargo;
import com.KayraAtalay.service.ICargoService;
import com.KayraAtalay.shared.response.RestBaseController;
import com.KayraAtalay.shared.response.RootEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.KayraAtalay.config.RestApis.*;

@RestController
@RequestMapping(path = CARGO)
@RequiredArgsConstructor
public class RestCargoControllerImpl extends RestBaseController implements IRestCargoController {
    private final ICargoService cargoService;


    @PostMapping(SAVE_CARGO)
    @Override
    @PreAuthorize("hasAuthority('USER')")
    public RootEntity<DtoCargo> saveCargo(@Valid @RequestBody DtoCargoIU request) {
        return ok(cargoService.saveCargo(request));
    }

    @PutMapping(CANCEL_CARGO)
    @Override
    @PreAuthorize("hasAuthority('USER')")
    public RootEntity<DtoCargo> cancelCargo(@PathVariable Long cargoId) {
        return ok(cargoService.cancelCargo(cargoId));
    }

    @PutMapping(UPDATE_CARGO_STATUS)
    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public RootEntity<DtoCargo> updateCargoStatus(@Valid @RequestBody UpdateStatusRequest request) {
        return ok(cargoService.updateCargoStatus(request));
    }

    @PutMapping(ACCEPT_CARGO)
    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public RootEntity<DtoCargo> acceptCargo(@RequestBody AcceptAndDeliverRequest acceptAndDeliverRequest) {
        return ok(cargoService.acceptCargo(acceptAndDeliverRequest));
    }

    @PutMapping(DELIVER_CARGO)
    @Override
    @PreAuthorize("hasAuthority('COURIER')")
    public RootEntity<DtoCargo> deliverCargo(@RequestBody AcceptAndDeliverRequest acceptAndDeliverRequest) {
        return ok(cargoService.deliverCargo(acceptAndDeliverRequest));
    }

    @GetMapping(FIND_CARGO_BY_TRACKING_NUMBER)
    @Override
    public RootEntity<DtoCargo> findByTrackingNumber(@RequestParam String trackingNumber) {
        return ok(cargoService.findByTrackingNumber(trackingNumber));
    }

    @GetMapping(FIND_USER_CARGOS)
    @Override
    @PreAuthorize("hasAuthority('USER')")
    public RootEntity<List<DtoCargo>> findCargosBySenderId() {
        return ok(cargoService.getCargosBySenderId()) ;
    }
}
