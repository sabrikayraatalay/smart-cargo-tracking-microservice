package com.KayraAtalay.repository;

import com.KayraAtalay.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {
    Optional<Cargo> findByTrackingNumber(String trackingNumber);
    List<Cargo> findAllBySenderId(Long senderId);
    boolean existsByTrackingNumberAndCreatedCode(String trackingNumber, Integer createdCode);
    boolean existsByTrackingNumberAndDeliveryCode(String trackingNumber, Integer deliveryCode);
    boolean existsByIdAndSenderId(Long id, Long senderId);
}
