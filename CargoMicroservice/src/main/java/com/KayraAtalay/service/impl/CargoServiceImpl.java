package com.KayraAtalay.service.impl;

import com.KayraAtalay.config.RabbitMQConfig;
import com.KayraAtalay.dto.request.DtoCargoIU;
import com.KayraAtalay.dto.request.UpdateStatusRequest;
import com.KayraAtalay.dto.response.DtoCargo;
import com.KayraAtalay.enums.CargoStatus;
import com.KayraAtalay.manager.UserManager;
import com.KayraAtalay.model.Cargo;
import com.KayraAtalay.repository.CargoRepository;
import com.KayraAtalay.service.ICargoService;
import com.KayraAtalay.shared.dto.NotificationMessage;
import com.KayraAtalay.utils.DtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class CargoServiceImpl implements ICargoService {
    private final CargoRepository cargoRepository;
    private final UserManager userManager;
    private final RabbitTemplate rabbitTemplate;

    private Long findUserId(){
       return userManager.findUserIdByUsername().getPayload();
    }

    private String generateTrackingNumber() {
        return "TR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private Integer generateDeliveryCode() {
        return ThreadLocalRandom.current().nextInt(1000, 10000);
    }

    private Cargo createCargo(DtoCargoIU request, Long userId) {
        return Cargo.builder()
                .deliveryCode(generateDeliveryCode())
                .receiverName(request.getReceiverName())
                .senderId(findUserId())
                .status(CargoStatus.CREATED)
                .trackingNumber(generateTrackingNumber())
                .build();
    }

    private void sendNotificationToQueue(Cargo cargo) {
        NotificationMessage message = new NotificationMessage();
        message.setTrackingNumber(cargo.getTrackingNumber());
        message.setReceiverEmail(cargo.getReceiverEmail());
        message.setReceiverName(cargo.getReceiverName());
        message.setDeliveryCode(cargo.getDeliveryCode());
        message.setMessageType("CARGO_CREATED");


        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                message
        );
    }

    @Override
    public DtoCargo saveCargo(DtoCargoIU request) {
         Long userId = findUserId();

       Cargo cargo = createCargo(request, userId);
       sendNotificationToQueue(cargo);

       Cargo savedCargo = cargoRepository.save(cargo);

       return DtoConverter.toDtoCargo(cargo);

    }

    @Override
    public DtoCargo updateCargoStatus(UpdateStatusRequest request) {
        return null;
    }

    @Override
    public DtoCargo findByTrackingNumber(String trackingNumber) {
        return null;
    }

    @Override
    public List<DtoCargo> getCargosBySenderId(Long senderId) {
        return List.of();
    }
}
