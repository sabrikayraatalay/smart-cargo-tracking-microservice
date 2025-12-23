package com.KayraAtalay.service.impl;

import com.KayraAtalay.config.RabbitMQConfig;
import com.KayraAtalay.dto.request.AcceptAndDeliverRequest;
import com.KayraAtalay.dto.request.DtoCargoIU;
import com.KayraAtalay.dto.request.UpdateStatusRequest;
import com.KayraAtalay.dto.response.DtoCargo;
import com.KayraAtalay.enums.CargoStatus;
import com.KayraAtalay.manager.UserManager;
import com.KayraAtalay.model.Cargo;
import com.KayraAtalay.repository.CargoRepository;
import com.KayraAtalay.service.ICargoService;
import com.KayraAtalay.shared.dto.NotificationMessage;
import com.KayraAtalay.shared.exception.BaseException;
import com.KayraAtalay.shared.exception.ErrorMessage;
import com.KayraAtalay.shared.exception.MessageType;
import com.KayraAtalay.utils.DtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CargoServiceImpl implements ICargoService {
    private final CargoRepository cargoRepository;
    private final UserManager userManager;
    private final RabbitTemplate rabbitTemplate;

    private Long findUserId(){
       return userManager.findUserIdByUsername().getPayload();
    }
    private String findUserEmail(){
        return userManager.findUserEmailByUsername().getPayload();
    }

    private String generateTrackingNumber() {
        return "TR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private Integer generateDeliveryCodeOrCreatedCode() {
        return ThreadLocalRandom.current().nextInt(1000, 10000);
    }



    private Cargo createCargo(DtoCargoIU request) {
        return Cargo.builder()
                .deliveryCode(generateDeliveryCodeOrCreatedCode())
                .receiverName(request.getReceiverName())
                .receiverEmail(request.getReceiverEmail())
                .deliveryAddress(request.getDeliveryAddress())
                .senderId(findUserId())
                .senderEmail(findUserEmail())
                .status(CargoStatus.CREATED)
                .trackingNumber(generateTrackingNumber())
                .createdCode(generateDeliveryCodeOrCreatedCode()) //added for user,they will say this code to admin and they accept it
                .build();
    }

    private void sendNotificationToQueueForCreatedCode(Cargo cargo, String messageType) {
        NotificationMessage message = new NotificationMessage();
        message.setTrackingNumber(cargo.getTrackingNumber());
        message.setReceiverEmail(cargo.getSenderEmail());
        message.setReceiverName(cargo.getReceiverName());
        message.setDeliveryCode(cargo.getDeliveryCode());

        message.setMessageType(messageType);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                message
        );
    }

    private void sendNotificationToQueue(Cargo cargo, String messageType) {
        NotificationMessage message = new NotificationMessage();
        message.setTrackingNumber(cargo.getTrackingNumber());
        message.setReceiverEmail(cargo.getReceiverEmail());
        message.setReceiverName(cargo.getReceiverName());
        message.setDeliveryCode(cargo.getDeliveryCode());

        message.setMessageType(messageType);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                message
        );
    }

    @Override
    public DtoCargo saveCargo(DtoCargoIU request) {

       Cargo cargo = createCargo(request);

       Cargo savedCargo = cargoRepository.save(cargo);
       sendNotificationToQueueForCreatedCode(savedCargo, "CARGO_CREATED");

       return DtoConverter.toDtoCargo(savedCargo);

    }

    @Override
    public DtoCargo cancelCargo(Long cargoId) {
        Long senderId = findUserId();
        boolean isExists = cargoRepository.existsByIdAndSenderId(cargoId, senderId );

        if (!isExists) {
            throw new BaseException(new ErrorMessage(MessageType.CARGO_NOT_FOUND, cargoId.toString()));
        }
        Optional<Cargo> optCargo = cargoRepository.findById(cargoId);

        if (optCargo.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.CARGO_NOT_FOUND, cargoId.toString()));
        }

        Cargo cargo = optCargo.get();

        if (cargo.getStatus() != CargoStatus.CREATED || cargo.getStatus() == CargoStatus.CANCELLED) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION,
                    "You can not cancel this cargo. This cargo is " + cargo.getStatus()));
        }

        cargo.setStatus(CargoStatus.CANCELLED);
        Cargo savedCargo = cargoRepository.save(cargo);

        return DtoConverter.toDtoCargo(savedCargo);
    }

    @Override
    public DtoCargo updateCargoStatus(UpdateStatusRequest request) {
        CargoStatus cargoStatus = request.getCargoStatus();

        if(cargoStatus.equals(CargoStatus.DELIVERED ) || request.getCargoStatus().equals(CargoStatus.RECEIVED) ){
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION,
                    "Can not update status that you want from this function"));
        }


        String trackingNumber = request.getTrackingNumber();
        Optional<Cargo> cargo = cargoRepository.findByTrackingNumber(trackingNumber);

        if(cargo.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.CARGO_NOT_FOUND, trackingNumber));
        }
        cargo.get().setStatus(cargoStatus);
        Cargo savedCargo = cargoRepository.save(cargo.get());

        return DtoConverter.toDtoCargo(savedCargo);

    }

    @Override
    public DtoCargo acceptCargo(AcceptAndDeliverRequest acceptAndDeliverRequest) {
        String trackingNumber = acceptAndDeliverRequest.getTrackingNumber();
        Integer deliveryCode = acceptAndDeliverRequest.getDeliveryOrCreatedCode();

        boolean isExists = cargoRepository.existsByTrackingNumberAndCreatedCode(trackingNumber, deliveryCode);
        if(!isExists){
            throw new BaseException(new ErrorMessage(MessageType.CARGO_NOT_FOUND, "can not find a cargo with given informations"));
        }

        Cargo cargo = cargoRepository.findByTrackingNumber(trackingNumber).get();

        cargo.setStatus(CargoStatus.RECEIVED);

        Cargo savedCargo = cargoRepository.save(cargo);
        sendNotificationToQueue(savedCargo,"Cargo_RECEIVED");

        return DtoConverter.toDtoCargo(savedCargo);

    }

    @Override
    public DtoCargo deliverCargo(AcceptAndDeliverRequest acceptAndDeliverRequest) {

        String trackingNumber = acceptAndDeliverRequest.getTrackingNumber();
        Integer deliveryCode = acceptAndDeliverRequest.getDeliveryOrCreatedCode();

        boolean isExists = cargoRepository.existsByTrackingNumberAndDeliveryCode(trackingNumber, deliveryCode);

        if (!isExists) {
            throw new BaseException(new ErrorMessage(MessageType.CARGO_NOT_FOUND, "Wrong Tracking Number or Delivery Code"));
        }
        Cargo cargo = cargoRepository.findByTrackingNumber(trackingNumber).get();


        cargo.setStatus(CargoStatus.DELIVERED);
        Cargo savedCargo = cargoRepository.save(cargo);

        sendNotificationToQueue(savedCargo,"Cargo_DELIVERED");

        return DtoConverter.toDtoCargo(savedCargo);
    }

    @Override
    public DtoCargo findByTrackingNumber(String trackingNumber) {
        Optional<Cargo> cargo = cargoRepository.findByTrackingNumber(trackingNumber);
        if(cargo.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.CARGO_NOT_FOUND, trackingNumber));
        }

        return DtoConverter.toDtoCargo(cargo.get());
    }

    @Override
    public List<DtoCargo> getCargosBySenderId() {
         List<Cargo> cargos = cargoRepository.findAllBySenderId(findUserId());

        return cargos.stream().map(DtoConverter::toDtoCargo).collect(Collectors.toList());
    }
}
