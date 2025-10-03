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

  private String requestId;

  private Long productId;

  private Long reservationQuantity;

  private Long reservationPrice;

  @Enumerated(EnumType.STRING)
  private ProductReservationStatus status;

  public Long getReservationPrice() {
    return reservationPrice;
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

  public enum ProductReservationStatus {
    RESERVED, CONFIRMED, CANCELED
  }
}
