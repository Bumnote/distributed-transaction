package com.example.product.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_reservations")
public class ProductReservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String requestId; // 요청 Id (= orderId) -> 멱등성 보장

  private Long productId; // 상품 Id

  private Long reservationQuantity; // 예약 수량

  private Long reservationPrice; // 예약 당시의 가격

  @Enumerated(EnumType.STRING)
  private ProductReservationStatus status; // 현재 예약 상태 (예약됨, 확정됨, 취소됨)

  public Long getReservationPrice() {
    return reservationPrice;
  }

  public Long getProductId() {
    return productId;
  }

  public Long getReservationQuantity() {
    return reservationQuantity;
  }

  public ProductReservationStatus getStatus() {
    return status;
  }

  public ProductReservation() {
  }

  public ProductReservation(String requestId, Long productId, Long reservationQuantity, Long reservationPrice) {
    this.requestId = requestId;
    this.productId = productId;
    this.reservationQuantity = reservationQuantity;
    this.reservationPrice = reservationPrice;
    this.status = ProductReservationStatus.RESERVED;
  }

  public void cancel() {
    if (this.status == ProductReservationStatus.CONFIRMED) {
      throw new RuntimeException("이미 확정된 예약은 취소할 수 없습니다.");
    }

    this.status = ProductReservationStatus.CANCELED;
  }

  public void confirm() {
    if (this.status == ProductReservationStatus.CANCELED) {
      throw new RuntimeException("이미 취소된 예약입니다.");
    }

    this.status = ProductReservationStatus.CONFIRMED;
  }

  // inner enum 클래스
  public enum ProductReservationStatus {
    RESERVED, CONFIRMED, CANCELED
  }
}
