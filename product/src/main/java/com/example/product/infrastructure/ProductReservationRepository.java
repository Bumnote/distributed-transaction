package com.example.product.infrastructure;

import com.example.product.domain.ProductReservation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReservationRepository extends JpaRepository<ProductReservation, Long> {

  List<ProductReservation> findAllByRequestId(String requestId);
}
