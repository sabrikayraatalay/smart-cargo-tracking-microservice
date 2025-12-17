package com.KayraAtalay.repository;

import com.KayraAtalay.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {
    Optional<Cargo> findByTrackingNumber(String trackingNumber);
    boolean existsByIdAndDeliveryCode(Long cargoId, Integer deliveryCode);
    List<Cargo> findAllBySenderId(Long senderId);
}
