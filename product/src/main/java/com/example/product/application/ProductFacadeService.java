package com.example.product.application;

import com.example.product.application.dto.ProductReserveCancelCommand;
import com.example.product.application.dto.ProductReserveCommand;
import com.example.product.application.dto.ProductReserveConfirmCommand;
import com.example.product.application.dto.ProductReserveResult;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
public class ProductFacadeService {

  private final ProductService productService;

  public ProductFacadeService(ProductService productService) {
    this.productService = productService;
  }

  public ProductReserveResult tryReserve(ProductReserveCommand command) {
    int tryCount = 0;

    while (tryCount < 3) {
      try {
        return productService.tryReserve(command);
      } catch (ObjectOptimisticLockingFailureException e) {
        tryCount++;
      }
    }

    throw new RuntimeException("예약에 실패했습니다.");
  }

  public void confirmReserve(ProductReserveConfirmCommand command) {
    int tryCount = 0;

    while (tryCount < 3) {
      try {
        productService.confirmReserve(command);
        return;
      } catch (ObjectOptimisticLockingFailureException e) {
        tryCount++;
      }
    }

    throw new RuntimeException("예약에 실패했습니다.");
  }

  public void cancelReserve(ProductReserveCancelCommand command) {
    int tryCount = 0;

    while (tryCount < 3) {
      try {
        productService.cancelReserve(command);
        return;
      } catch (ObjectOptimisticLockingFailureException e) {
        tryCount++;
      }
    }

    throw new RuntimeException("예약에 실패했습니다.");
  }
}