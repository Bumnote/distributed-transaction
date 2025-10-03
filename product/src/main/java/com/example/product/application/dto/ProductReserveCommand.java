package com.example.product.application.dto;

import java.util.List;

public record ProductReserveCommand(
    String requestId,
    List<ReserveTime> items
) {

  public record ReserveTime(
      Long productId,
      Long reserveQuantity
  ) {}
}
