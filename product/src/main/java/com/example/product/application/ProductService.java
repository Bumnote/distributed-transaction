package com.example.product.application;

import com.example.product.application.dto.ProductReserveCancelCommand;
import com.example.product.application.dto.ProductReserveCommand;
import com.example.product.application.dto.ProductReserveConfirmCommand;
import com.example.product.application.dto.ProductReserveResult;
import com.example.product.domain.Product;
import com.example.product.domain.ProductReservation;
import com.example.product.domain.ProductReservation.ProductReservationStatus;
import com.example.product.infrastructure.ProductRepository;
import com.example.product.infrastructure.ProductReservationRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

  private final ProductRepository productRepository;
  private final ProductReservationRepository productReservationRepository;

  public ProductService(
      ProductRepository productRepository,
      ProductReservationRepository productReservationRepository
  ) {
    this.productRepository = productRepository;
    this.productReservationRepository = productReservationRepository;
  }

  // 예약 메서드 트랜잭션 처리
  @Transactional
  public ProductReserveResult tryReserve(ProductReserveCommand command) {
    List<ProductReservation> exists = productReservationRepository.findAllByRequestId(command.requestId());

    if (!exists.isEmpty()) {
      Long totalPrice = exists.stream().mapToLong(ProductReservation::getReservationPrice).sum();

      return new ProductReserveResult(totalPrice);
    }

    Long totalPrice = 0L;
    for (ProductReserveCommand.ReserveItem item : command.items()) {
      Product product = productRepository.findById(item.productId()).orElseThrow();

      Long price = product.reserve(item.reserveQuantity());
      totalPrice += price;

      productRepository.save(product);
      productReservationRepository.save(
          new ProductReservation(
              command.requestId(),
              item.productId(),
              item.reserveQuantity(),
              price
          )
      );
    }

    return new ProductReserveResult(totalPrice);
  }

  @Transactional
  public void confirmReserve(ProductReserveConfirmCommand command) {

    List<ProductReservation> reservations = productReservationRepository.findAllByRequestId(command.requestId());

    if (reservations.isEmpty()) {
      throw new RuntimeException("예약된 정보가 없습니다.");
    }

    boolean alreadyConfirmed = reservations.stream()
        .anyMatch(item -> item.getStatus() == ProductReservationStatus.CONFIRMED);

    if (alreadyConfirmed) {
      System.out.println("이미 확정이 되었습니다.");
      return;
    }

    for (ProductReservation reservation : reservations) {
      Product product = productRepository.findById(reservation.getProductId()).orElseThrow();

      product.confirm(reservation.getReservationQuantity());
      reservation.confirm();

      productRepository.save(product);
      productReservationRepository.save(reservation);
    }
  }

  public void cancelReserve(ProductReserveCancelCommand command) {
    List<ProductReservation> reservations = productReservationRepository.findAllByRequestId(command.requestId());

    if (reservations.isEmpty()) {
      throw new RuntimeException("예약된 정보가 존재하지 않습니다.");
    }

    boolean alreadyCancelled = reservations.stream()
        .anyMatch(item -> item.getStatus() == ProductReservationStatus.CANCELED);

    if (alreadyCancelled) {
      System.out.println("이미 취소된 예약입니다.");
      return;
    }

    for (ProductReservation reservation : reservations) {
      Product product = productRepository.findById(reservation.getProductId()).orElseThrow();

      product.cancel(reservation.getReservationQuantity());
      reservation.cancel();

      productRepository.save(product);
      productReservationRepository.save(reservation);
    }
  }

}
